package ar.com.kelia.presentation.controller;
import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.usecase.GetRandomProductsUseCase;
import ar.com.kelia.domain.usecase.SearchProductUseCase;
import ar.com.kelia.presentation.controller.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private SearchProductUseCase searchProductUseCase;

    @Mock
    private GetRandomProductsUseCase getRandomProductsUseCase;

    @InjectMocks
    private ProductController productController;

    private Product mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = new Product("Test Product", "https://example.com/image.jpg",
                "https://example.com/product", "Test Brand", true);
    }

    @Test
    void searchProduct_ShouldReturnProducts_WhenValidTerm() {
        String searchTerm = "test";
        List<Product> expectedProducts = Arrays.asList(mockProduct);
        when(searchProductUseCase.execute(searchTerm)).thenReturn(expectedProducts);

        ResponseEntity<List<Product>> response = productController.searchProduct(searchTerm);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(mockProduct, response.getBody().get(0));
        verify(searchProductUseCase, times(1)).execute(searchTerm);
        verify(getRandomProductsUseCase, never()).execute();
    }

    @Test
    void searchProduct_ShouldReturnEmptyList_WhenNoProductsFound() {
        String searchTerm = "nonexistent";
        when(searchProductUseCase.execute(searchTerm)).thenReturn(Arrays.asList());

        ResponseEntity<List<Product>> response = productController.searchProduct(searchTerm);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(searchProductUseCase, times(1)).execute(searchTerm);
        verify(getRandomProductsUseCase, never()).execute();
    }

    @Test
    void searchProduct_ShouldReturnMultipleProducts() {
        String searchTerm = "test";
        Product product1 = new Product("Product 1", "thumb1", "link1", "Brand1", true);
        Product product2 = new Product("Product 2", "thumb2", "link2", "Brand2", false);
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(searchProductUseCase.execute(searchTerm)).thenReturn(expectedProducts);

        ResponseEntity<List<Product>> response = productController.searchProduct(searchTerm);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(product1, response.getBody().get(0));
        assertEquals(product2, response.getBody().get(1));
        verify(searchProductUseCase, times(1)).execute(searchTerm);
        verify(getRandomProductsUseCase, never()).execute();
    }

    @Test
    void searchProduct_ShouldCallGetRandomProductsUseCase_WhenTermIsNull() {
        when(getRandomProductsUseCase.execute()).thenReturn(Arrays.asList(mockProduct));

        ResponseEntity<List<Product>> response = productController.searchProduct(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(mockProduct, response.getBody().get(0));
        verify(getRandomProductsUseCase, times(1)).execute();
        verify(searchProductUseCase, never()).execute(any());
    }

    @Test
    void searchProduct_ShouldCallGetRandomProductsUseCase_WhenTermIsEmpty() {
        when(getRandomProductsUseCase.execute()).thenReturn(Arrays.asList(mockProduct));

        ResponseEntity<List<Product>> response = productController.searchProduct("");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(mockProduct, response.getBody().get(0));
        verify(getRandomProductsUseCase, times(1)).execute();
        verify(searchProductUseCase, never()).execute(any());
    }
}