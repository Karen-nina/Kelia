package ar.com.kelia.infrastructure.provider;

import ar.com.kelia.infrastructure.dto.TokenMLDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TokenMLApiClient {

    @Value("${mercadolibre.client_id}")
    private String clientId;

    @Value("${mercadolibre.client_secret}")
    private String clientSecret;

    @Value("${mercadolibre.redirect_uri}")
    private String redirectUri;

    @Value("${mercadolibre.url_token}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public TokenMLDTO getToken(String grantType, String codeOrRefreshToken) {
        String url = this.url;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> request = getMultiValueMapHttpEntity(grantType, codeOrRefreshToken, headers);

        ResponseEntity<TokenMLDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                TokenMLDTO.class
        );

        return response.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> getMultiValueMapHttpEntity(String grantType, String codeOrRefreshToken, HttpHeaders headers) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        if ("authorization_code".equals(grantType)) {
            body.add("code", codeOrRefreshToken);
            body.add("redirect_uri", redirectUri);
        } else if ("refresh_token".equals(grantType)) {
            body.add("refresh_token", codeOrRefreshToken);
        } else {
            throw new IllegalArgumentException("Unsupported grant type: " + grantType);
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return request;
    }
}
