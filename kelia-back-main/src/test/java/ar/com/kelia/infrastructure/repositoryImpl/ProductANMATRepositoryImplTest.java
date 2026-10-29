package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JpaProductRepository;
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
 * Clase de pruebas para la implementación del repositorio de productos ANMAT.
 * Prueba la funcionalidad de guardado, eliminación y búsqueda de productos
 * ANMAT en la base de datos.
 */
@ExtendWith(MockitoExtension.class)
class ProductANMATRepositoryImplTest {
    @Mock
    private JpaProductRepository jpaRepository;

    @InjectMocks
    private ProductANMATRepositoryImpl repository;

    private ProductANMAT product;

    /**
     * Configuración inicial para cada prueba.
     * Crea un objeto mock de ProductANMAT para usar en las pruebas.
     */
    @BeforeEach
    void setUp() {
        product = new ProductANMAT("Brand", "Trade", "Sales", "Type", "Status");
    }

    /**
     * Prueba que el método saveAll delegue correctamente al repositorio JPA.
     * Verifica que se guarde la lista de productos en la base de datos.
     */
    @Test
    void saveAll_ShouldDelegateToJpaRepository() {
        // Arrange - Preparar datos de prueba
        List<ProductANMAT> products = Arrays.asList(product);

        // Act - Ejecutar el método a probar
        repository.saveAll(products);

        // Assert - Verificar resultados esperados
        verify(jpaRepository, times(1)).saveAll(products);
    }

    /**
     * Prueba que el método deleteAll delegue correctamente al repositorio JPA.
     * Verifica que se eliminen todos los productos de la base de datos.
     */
    @Test
    void deleteAll_ShouldDelegateToJpaRepository() {
        // Act - Ejecutar el método a probar
        repository.deleteAll();

        // Assert - Verificar resultados esperados
        verify(jpaRepository, times(1)).deleteAll();
    }

    /**
     * Prueba que el método findAll devuelva todos los productos.
     * Verifica que se obtengan todos los productos desde la base de datos.
     */
    @Test
    void findAll_ShouldReturnAllProducts() {
        // Arrange - Preparar datos de prueba
        List<ProductANMAT> expected = Arrays.asList(product);
        when(jpaRepository.findAll()).thenReturn(expected);

        // Act - Ejecutar el método a probar
        List<ProductANMAT> result = repository.findAll();

        // Assert - Verificar resultados esperados
        assertEquals(expected, result);
        verify(jpaRepository, times(1)).findAll();
    }

    /**
     * Prueba que el método getSimilarity llame al repositorio JPA con los umbrales
     * correctos.
     * Verifica que se busquen productos similares con los parámetros especificados.
     */
    @Test
    void getSimilarity_ShouldCallJpaRepositoryWithThresholds() {
        // Arrange - Preparar datos de prueba
        List<ProductANMAT> expected = Arrays.asList(product);
        when(jpaRepository.findSimilarProducts(anyString(), anyString(), anyDouble()))
                .thenReturn(expected);

        // Act - Ejecutar el método a probar
        List<ProductANMAT> result = repository.getSimilarity("brand", "desc");

        // Assert - Verificar resultados esperados
        assertEquals(expected, result);
        verify(jpaRepository, times(1)).findSimilarProducts(anyString(), anyString(), anyDouble());
    }
}