package ar.com.kelia.infrastructure.provider;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.domain.service.SaveTokenMLService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
class MercadoLibreProductClientTest {

    @Mock
    private SaveTokenMLService saveTokenMLService;

    @InjectMocks
    private MercadoLibreProductClient mercadoLibreProductClient;

    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        ReflectionTestUtils.setField(mercadoLibreProductClient, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(mercadoLibreProductClient, "url",
                "https://api.mercadolibre.com/sites/MLB/search?q=");
    }

    @Test
    void searchProducts_WithValidResponse_ShouldReturnProducts() {
        TokenML mockToken = new TokenML();
        mockToken.setAccessToken("test-access-token");
        mockToken.setExpiresAt(LocalDateTime.now().plusHours(1));

        String searchTerm = "harina";
        String expectedResponse = """
                {
                    "results": [
                        {
                            "id": "MLB123456",
                            "name": "Harina Sin Gluten",
                            "pictures": [
                                {"url": "https://example.com/image.jpg"}
                            ],
                            "attributes": [
                                {"id": "IS_GLUTEN_FREE", "value_name": "Sí"},
                                {"id": "BRAND", "value_name": "Test Brand"}
                            ]
                        }
                    ]
                }
                """;

        when(saveTokenMLService.getValidToken()).thenReturn(mockToken);

        mockServer.expect(requestTo("https://api.mercadolibre.com/sites/MLB/search?q=harina%20sin%20tacc&limit=7"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Bearer test-access-token"))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        List<Product> result = mercadoLibreProductClient.searchProducts(searchTerm);

        assertNotNull(result);
        assertEquals(1, result.size());
        Product product = result.get(0);
        assertEquals("Harina Sin Gluten", product.getTitle());
        assertEquals("https://example.com/image.jpg", product.getThumbnail());
        assertEquals("https://www.mercadolibre.com.ar/p/MLB123456", product.getLink());
        assertEquals("Test Brand", product.getSeller());
        assertFalse(product.isCertificate());

        mockServer.verify();
    }

    @Test
    void searchProducts_WithNoGlutenFreeProducts_ShouldReturnEmptyList() {
        TokenML mockToken = new TokenML();
        mockToken.setAccessToken("test-access-token");
        mockToken.setExpiresAt(LocalDateTime.now().plusHours(1));

        String searchTerm = "pan";
        String expectedResponse = """
                {
                    "results": [
                        {
                            "id": "MLB789012",
                            "name": "Pan Con Gluten",
                            "pictures": [
                                {"url": "https://example.com/image2.jpg"}
                            ],
                            "attributes": [
                                {"id": "IS_GLUTEN_FREE", "value_name": "No"},
                                {"id": "BRAND", "value_name": "Another Brand"}
                            ]
                        }
                    ]
                }
                """;

        when(saveTokenMLService.getValidToken()).thenReturn(mockToken);

        mockServer.expect(requestTo("https://api.mercadolibre.com/sites/MLB/search?q=pan%20sin%20tacc&limit=7"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Bearer test-access-token"))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        List<Product> result = mercadoLibreProductClient.searchProducts(searchTerm);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        mockServer.verify();
    }

    @Test
    void searchProducts_WithMissingBrand_ShouldUseDefaultBrand() {
        TokenML mockToken = new TokenML();
        mockToken.setAccessToken("test-access-token");
        mockToken.setExpiresAt(LocalDateTime.now().plusHours(1));

        String searchTerm = "producto";
        String expectedResponse = """
                {
                    "results": [
                        {
                            "id": "MLB345678",
                            "name": "Producto Sin Gluten",
                            "pictures": [
                                {"url": "https://example.com/image3.jpg"}
                            ],
                            "attributes": [
                                {"id": "IS_GLUTEN_FREE", "value_name": "Sí"}
                            ]
                        }
                    ]
                }
                """;

        when(saveTokenMLService.getValidToken()).thenReturn(mockToken);

        mockServer.expect(requestTo("https://api.mercadolibre.com/sites/MLB/search?q=producto%20sin%20tacc&limit=7"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Bearer test-access-token"))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        List<Product> result = mercadoLibreProductClient.searchProducts(searchTerm);

        assertNotNull(result);
        assertEquals(1, result.size());

        Product product = result.get(0);
        assertEquals("Producto Sin Gluten", product.getTitle());
        assertEquals("Desconocido", product.getSeller());

        mockServer.verify();
    }

    @Test
    void searchProducts_WithNoPictures_ShouldHandleNullThumbnail() {
        TokenML mockToken = new TokenML();
        mockToken.setAccessToken("test-access-token");
        mockToken.setExpiresAt(LocalDateTime.now().plusHours(1));

        String searchTerm = "producto";
        String expectedResponse = """
                {
                    "results": [
                        {
                            "id": "MLB901234",
                            "name": "Producto Sin Gluten Sin Imagen",
                            "pictures": [],
                            "attributes": [
                                {"id": "IS_GLUTEN_FREE", "value_name": "Sí"},
                                {"id": "BRAND", "value_name": "Test Brand"}
                            ]
                        }
                    ]
                }
                """;

        when(saveTokenMLService.getValidToken()).thenReturn(mockToken);

        mockServer.expect(requestTo("https://api.mercadolibre.com/sites/MLB/search?q=producto%20sin%20tacc&limit=7"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Bearer test-access-token"))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        List<Product> result = mercadoLibreProductClient.searchProducts(searchTerm);

        assertNotNull(result);
        assertEquals(1, result.size());

        Product product = result.get(0);
        assertEquals("Producto Sin Gluten Sin Imagen", product.getTitle());
        assertNull(product.getThumbnail());

        mockServer.verify();
    }
}
