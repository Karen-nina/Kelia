package ar.com.kelia.infrastructure.provider;

import ar.com.kelia.infrastructure.dto.TokenMLDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Clase de pruebas para el cliente API de tokens de MercadoLibre.
 * Prueba la funcionalidad de obtención de tokens de acceso usando diferentes
 * tipos de grant.
 */
@ExtendWith(MockitoExtension.class)
class TokenMLApiClientTest {

    @InjectMocks
    private TokenMLApiClient tokenMLApiClient;

    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    /**
     * Configuración inicial para cada prueba.
     * Configura el servidor mock y los valores de prueba para las credenciales.
     */
    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        // Inyectar el RestTemplate mockeado
        ReflectionTestUtils.setField(tokenMLApiClient, "restTemplate", restTemplate);

        // Configurar valores de prueba
        ReflectionTestUtils.setField(tokenMLApiClient, "clientId", "test-client-id");
        ReflectionTestUtils.setField(tokenMLApiClient, "clientSecret", "test-client-secret");
        ReflectionTestUtils.setField(tokenMLApiClient, "redirectUri", "http://localhost:8080/callback");
        ReflectionTestUtils.setField(tokenMLApiClient, "url", "https://api.mercadolibre.com/oauth/token");
    }

    /**
     * Prueba que el método getToken devuelva un token cuando se usa
     * authorization_code.
     * Verifica que se haga la petición correcta a la API y se procese la respuesta.
     */
    @Test
    void getToken_WithAuthorizationCode_ShouldReturnToken() {
        // Arrange - Preparar datos de prueba
        String grantType = "authorization_code";
        String code = "test-code";
        String expectedResponse = """
                {
                    "access_token": "test-access-token",
                    "token_type": "Bearer",
                    "expires_in": 3600,
                    "refresh_token": "test-refresh-token"
                }
                """;

        mockServer.expect(requestTo("https://api.mercadolibre.com/oauth/token"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("grant_type=authorization_code")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("client_id=test-client-id")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("client_secret=test-client-secret")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("code=test-code")))
                .andExpect(content()
                        .string(org.hamcrest.Matchers
                                .containsString("redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fcallback")))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // Act - Ejecutar el método a probar
        TokenMLDTO result = tokenMLApiClient.getToken(grantType, code);

        // Assert - Verificar resultados esperados
        assertNotNull(result);
        assertEquals("test-access-token", result.getAccessToken());
        assertEquals("Bearer", result.getTokenType());
        assertEquals(3600, result.getExpiresIn());
        assertEquals("test-refresh-token", result.getRefreshToken());

        mockServer.verify();
    }

    /**
     * Prueba que el método getToken devuelva un token cuando se usa refresh_token.
     * Verifica que se haga la petición correcta a la API y se procese la respuesta.
     */
    @Test
    void getToken_WithRefreshToken_ShouldReturnToken() {
        // Arrange - Preparar datos de prueba
        String grantType = "refresh_token";
        String refreshToken = "test-refresh-token";
        String expectedResponse = """
                {
                    "access_token": "new-access-token",
                    "token_type": "Bearer",
                    "expires_in": 3600,
                    "refresh_token": "new-refresh-token"
                }
                """;

        mockServer.expect(requestTo("https://api.mercadolibre.com/oauth/token"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("grant_type=refresh_token")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("client_id=test-client-id")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("client_secret=test-client-secret")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("refresh_token=test-refresh-token")))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // Act - Ejecutar el método a probar
        TokenMLDTO result = tokenMLApiClient.getToken(grantType, refreshToken);

        // Assert - Verificar resultados esperados
        assertNotNull(result);
        assertEquals("new-access-token", result.getAccessToken());
        assertEquals("Bearer", result.getTokenType());
        assertEquals(3600, result.getExpiresIn());
        assertEquals("new-refresh-token", result.getRefreshToken());

        mockServer.verify();
    }

    /**
     * Prueba que el método getToken lance una excepción cuando se usa un tipo de
     * grant no soportado.
     * Verifica que se valide correctamente el tipo de grant antes de hacer la
     * petición.
     */
    @Test
    void getToken_WithUnsupportedGrantType_ShouldThrowException() {
        // Arrange - Preparar datos de prueba
        String grantType = "unsupported_grant";
        String code = "test-code";

        // Act & Assert - Ejecutar y verificar excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tokenMLApiClient.getToken(grantType, code));

        assertEquals("Unsupported grant type: unsupported_grant", exception.getMessage());
    }
}