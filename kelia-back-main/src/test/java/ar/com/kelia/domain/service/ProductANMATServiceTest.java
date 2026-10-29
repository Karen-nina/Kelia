package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.repository.ProductANMATRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductANMATServiceTest {

    @Mock
    private ProductANMATRepository productRepository;

    @InjectMocks
    private ProductANMATService productANMATService;

    private ProductANMAT mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = new ProductANMAT(
                "Test Brand",
                "Test Trade Name",
                "Test Sales Name",
                "Test Product Type",
                "VIGENTE");
    }

    @Test
    void filterByMatchPercentage_ShouldReturnProductsFromRepository() {
        // Arrange
        String brand = "Test Brand";
        String description = "Test Description";
        List<ProductANMAT> expectedProducts = Arrays.asList(mockProduct);

        when(productRepository.getSimilarity(brand, description)).thenReturn(expectedProducts);

        // Act
        List<ProductANMAT> result = productANMATService.filterByMatchPercentage(brand, description);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProduct, result.get(0));
    }

    @Test
    void filterByMatchPercentage_ShouldReturnEmptyList_WhenNoMatches() {
        // Arrange
        String brand = "Nonexistent Brand";
        String description = "Nonexistent Description";

        when(productRepository.getSimilarity(brand, description)).thenReturn(Arrays.asList());

        // Act
        List<ProductANMAT> result = productANMATService.filterByMatchPercentage(brand, description);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void filterByMatchPercentage_ShouldReturnMultipleProducts() {
        // Arrange
        String brand = "Test Brand";
        String description = "Test Description";

        ProductANMAT product1 = new ProductANMAT("Brand1", "Trade1", "Sales1", "Type1", "VIGENTE");
        ProductANMAT product2 = new ProductANMAT("Brand2", "Trade2", "Sales2", "Type2", "VIGENTE");
        List<ProductANMAT> expectedProducts = Arrays.asList(product1, product2);

        when(productRepository.getSimilarity(brand, description)).thenReturn(expectedProducts);

        // Act
        List<ProductANMAT> result = productANMATService.filterByMatchPercentage(brand, description);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }

    @Test
    void filterByMatchPercentage_ShouldHandleNullParameters() {
        // Arrange
        String brand = null;
        String description = null;
        List<ProductANMAT> expectedProducts = Arrays.asList(mockProduct);

        when(productRepository.getSimilarity(brand, description)).thenReturn(expectedProducts);

        // Act
        List<ProductANMAT> result = productANMATService.filterByMatchPercentage(brand, description);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
