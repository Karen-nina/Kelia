package ar.com.kelia.infrastructure.provider;

import ar.com.kelia.domain.model.PlaceDetail;
import ar.com.kelia.domain.service.GooglePlacesService;
import ar.com.kelia.infrastructure.adapter.GooglePlacesParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GooglePlacesProvider  implements GooglePlacesService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final GooglePlacesParser parser;

    public GooglePlacesProvider(GooglePlacesParser parser) {
        this.parser = parser;
    }

    @Override
    public PlaceDetail getPlaceDetails(String placeId) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json")
                    .queryParam("place_id", placeId)
                    .queryParam("key", apiKey)
                    .queryParam("language", "es")
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            System.out.println("Respuesta Google Places: " + response);

            JSONObject json = new JSONObject(response);

            if (!json.getString("status").equals("OK")) {
                throw new RuntimeException("Google Places API Error: " + json.getString("status"));
            }

            JSONObject result = json.getJSONObject("result");
            return parser.parse(result);

        } catch (Exception e) {
            System.err.println("Error al llamar a Google Places API: " + e.getMessage());
            e.printStackTrace(System.err);
            throw new RuntimeException("Error interno al obtener detalles del lugar");
        }
    }
}
