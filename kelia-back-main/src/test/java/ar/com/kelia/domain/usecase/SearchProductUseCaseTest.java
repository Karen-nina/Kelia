package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para el caso de uso de búsqueda de productos.
 * Prueba la funcionalidad de ejecución del caso de uso que delega al servicio
 * de búsqueda.
 */
@ExtendWith(MockitoExtension.class)
class SearchProductUseCaseTest {

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchProductUseCase searchProductUseCase;

    private Product mockProduct;

    /**
     * Configuración inicial para cada prueba.
     * Crea un objeto mock de Product para usar en las pruebas.
     */
    @BeforeEach
    void setUp() {
        mockProduct = new Product(
                "Test Product",
                "https://example.com/image.jpg",
                "https://example.com/product",
                "Test Brand",
                true);
    }

    /**
     * Prueba que el método execute devuelva productos desde el servicio.
     * Verifica que se delegue correctamente la búsqueda al servicio y se devuelvan
     * los resultados.
     */
    @Test
    void execute_ShouldReturnProductsFromService() {
        // Arrange - Preparar datos de prueba
        String searchTerm = "test";
        List<Product> expectedProducts = Arrays.asList(mockProduct);
        when(searchService.searchProduct(searchTerm)).thenReturn(expectedProducts);

        // Act - Ejecutar el método a probar
        List<Product> result = searchProductUseCase.execute(searchTerm);

        // Assert - Verificar resultados esperados
        assertNotNull(result);
        assertEquals(expectedProducts, result);
        assertEquals(1, result.size());
        assertEquals(mockProduct, result.get(0));
        verify(searchService, times(1)).searchProduct(searchTerm);
    }

    /**
     * Prueba que el método execute devuelva lista vacía cuando no se encuentran
     * productos.
     * Verifica que se maneje correctamente el caso cuando no hay resultados de
     * búsqueda.
     */
    @Test
    void execute_ShouldReturnEmptyList_WhenNoProductsFound() {
        // Arrange - Preparar datos de prueba
        String searchTerm = "nonexistent";
        when(searchService.searchProduct(searchTerm)).thenReturn(Arrays.asList());

        // Act - Ejecutar el método a probar
        List<Product> result = searchProductUseCase.execute(searchTerm);

        // Assert - Verificar resultados esperados
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(searchService, times(1)).searchProduct(searchTerm);
    }

    /**
     * Prueba que el método execute devuelva múltiples productos.
     * Verifica que se procesen correctamente varios productos en los resultados.
     */
    @Test
    void execute_ShouldReturnMultipleProducts() {
        // Arrange - Preparar datos de prueba
        String searchTerm = "test";
        Product product1 = new Product("Product 1", "thumb1", "link1", "Brand1", true);
        Product product2 = new Product("Product 2", "thumb2", "link2", "Brand2", false);
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(searchService.searchProduct(searchTerm)).thenReturn(expectedProducts);

        // Act - Ejecutar el método a probar
        List<Product> result = searchProductUseCase.execute(searchTerm);

        // Assert - Verificar resultados esperados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
        verify(searchService, times(1)).searchProduct(searchTerm);
    }

    /**
     * Prueba que el método execute maneje correctamente términos de búsqueda nulos.
     * Verifica que se maneje correctamente el caso cuando el término de búsqueda es
     * null.
     */
    @Test
    void execute_ShouldHandleNullSearchTerm() {
        // Arrange - Preparar datos de prueba
        String searchTerm = null;
        when(searchService.searchProduct(searchTerm)).thenReturn(Arrays.asList());

        // Act - Ejecutar el método a probar
        List<Product> result = searchProductUseCase.execute(searchTerm);

        // Assert - Verificar resultados esperados
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(searchService, times(1)).searchProduct(searchTerm);
    }
}