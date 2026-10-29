package ar.com.kelia.infrastructure.provider;

import ar.com.kelia.domain.model.PlaceDetail;
import ar.com.kelia.infrastructure.adapter.GooglePlacesParser;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GooglePlacesProviderTest {
    private GooglePlacesProvider provider;
    private RestTemplate restTemplate;
    private GooglePlacesParser parser;

    @BeforeEach
    void setUp() throws Exception {
        parser = mock(GooglePlacesParser.class);
        provider = new GooglePlacesProvider(parser);
        restTemplate = mock(RestTemplate.class);
        // Setear apiKey por reflection
        Field apiKey = GooglePlacesProvider.class.getDeclaredField("apiKey");
        apiKey.setAccessible(true);
        apiKey.set(provider, "fake-key");
        // Setear RestTemplate privado y final
        Field restTemplateField = GooglePlacesProvider.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(provider, restTemplate);
    }

    @Test
    void testGetPlaceDetailsExitoso() {
        // Respuesta exitosa de Google
        String json = "{\"status\":\"OK\",\"result\":{\"name\":\"Plaza\"}}";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(json);
        when(parser.parse(any(JSONObject.class))).thenReturn(new PlaceDetail());
        PlaceDetail result = provider.getPlaceDetails("abc");
        assertNotNull(result);
    }

    @Test
    void testGetPlaceDetailsStatusError() {
        // Respuesta con status distinto de OK
        String json = "{\"status\":\"ZERO_RESULTS\"}";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(json);
        // Verificar que se lance una excepción cuando el status no es OK
        assertThrows(RuntimeException.class, () -> provider.getPlaceDetails("abc"));
    }

    @Test
    void testGetPlaceDetailsExcepcionRestTemplate() {
        // Excepción lanzada por RestTemplate
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException("Fallo HTTP"));
        Exception ex = assertThrows(RuntimeException.class, () -> provider.getPlaceDetails("abc"));
        assertTrue(ex.getMessage().contains("Error interno"));
    }

    @Test
    void testParseoCorrecto() {
        // Verifica que se llama al parser con el JSON correcto
        String json = "{\"status\":\"OK\",\"result\":{\"name\":\"Plaza\"}}";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(json);
        PlaceDetail esperado = new PlaceDetail();
        when(parser.parse(any(JSONObject.class))).thenReturn(esperado);
        PlaceDetail result = provider.getPlaceDetails("abc");
        assertSame(esperado, result);
    }
}