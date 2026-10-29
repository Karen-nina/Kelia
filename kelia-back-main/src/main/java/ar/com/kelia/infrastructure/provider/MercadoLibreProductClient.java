package ar.com.kelia.infrastructure.provider;


import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.domain.service.MercadoLibreProductService;
import ar.com.kelia.domain.service.SaveTokenMLService;
import ar.com.kelia.infrastructure.dto.ProductDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MercadoLibreProductClient implements MercadoLibreProductService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SaveTokenMLService service;

    @Value("${mercadolibre.url}")
    private String url;

    public MercadoLibreProductClient(SaveTokenMLService service) {
         this.service = service;
     }


    @Override
    public List<Product> searchProducts(String term) {
        List<Product> products = new ArrayList<>();

        try {
            TokenML tokenML = service.getValidToken();
            String url = this.url +
                    term + " sin tacc" + "&limit=" + "7";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + tokenML.getAccessToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode results = root.path("results");

                if (results.isArray()) {
                    for (JsonNode item : results) {
                        boolean isGlutenFree = false;
                        for (JsonNode attr : item.path("attributes")) {
                            if ("IS_GLUTEN_FREE".equals(attr.path("id").asText()) &&
                                    "Sí".equalsIgnoreCase(attr.path("value_name").asText())) {
                                isGlutenFree = true;
                                break;
                            }
                        }

                        if (!isGlutenFree) continue;

                        String brandName = "Desconocido";
                        for (JsonNode attr : item.path("attributes")) {
                            if ("BRAND".equals(attr.path("id").asText())) {
                                brandName = attr.path("value_name").asText();
                                break;
                            }
                        }

                        String title = item.path("name").asText(null);
                        String thumbnail = item.path("pictures").isArray() && !item.path("pictures").isEmpty()
                                ? item.path("pictures").get(0).path("url").asText(null)
                                : null;
                        String link = "https://www.mercadolibre.com.ar/p/" + item.path("id").asText(null);

                        products.add(new Product(title, thumbnail, link, brandName, false));
                    }
                } else {
                    System.err.println("No valid results found in the API response.");
                }
            } else {
                System.err.println("API response error: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("HTTP error while calling MercadoLibre API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return products;
    }
}
