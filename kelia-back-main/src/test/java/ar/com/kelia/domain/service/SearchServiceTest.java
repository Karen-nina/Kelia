package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.model.ProductANMAT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock
    MercadoLibreProductService mlClient;

    @Mock
    ProductANMATService anmatService;

    @InjectMocks
    SearchService searchService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCertifiedProductsWithAdjustedTitle() {
        Product mlProduct = new Product("Yerba mate", "thumb.jpg", "http://...", "Playadito", false);

        ProductANMAT match = new ProductANMAT(
                "La Tranquera",
                "NO REGISTRA",
                "Mate Verde",
                "Alimento",
                "VIGENTE"
        );

        when(mlClient.searchProducts("yerba")).thenReturn(List.of(mlProduct));
        when(anmatService.filterByMatchPercentage("Playadito", "Yerba mate")).thenReturn(List.of(match));

        List<Product> result = searchService.getProductsCertificates("yerba");

        assertEquals(1, result.size());
        Product certified = result.get(0);
        assertEquals("La Tranquera", certified.getTitle()); // título ajustado
        assertTrue(certified.isCertificate());
    }

    @Test
    void shouldAvoidDuplicateThumbnails() {
        Product p1 = new Product("A", "shared.jpg", "link1", "SellerA", false);
        Product p2 = new Product("B", "shared.jpg", "link2", "SellerB", false);

        ProductANMAT match = new ProductANMAT("MarcaX", "Producto", "Producto", "Tipo", "VIGENTE");

        when(mlClient.searchProducts("jugo")).thenReturn(List.of(p1, p2));
        when(anmatService.filterByMatchPercentage(anyString(), anyString())).thenReturn(List.of(match));

        List<Product> result = searchService.getProductsCertificates("jugo");

        assertEquals(1, result.size()); // solo uno con thumbnail único
    }

    @Test
    void shouldLimitResultsToFive() {
        ProductANMAT match = new ProductANMAT("Marca", "Producto", "Producto", "Tipo", "VIGENTE");

        List<Product> mockProducts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mockProducts.add(new Product("Prod" + i, "thumb" + i, "link" + i, "Seller", false));
        }

        when(mlClient.searchProducts("galletitas")).thenReturn(mockProducts);
        when(anmatService.filterByMatchPercentage(any(), any())).thenReturn(List.of(match));

        List<Product> result = searchService.getProductsCertificates("galletitas");

        assertEquals(5, result.size()); // limitado a máximo cinco
    }

    @Test
    void shouldReturnEmptyListIfNoMatches() {
        when(mlClient.searchProducts("inexistente")).thenReturn(Collections.emptyList());

        List<Product> result = searchService.searchProduct("inexistente");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUseRandomTermFromFoodList() {
        Product mockProduct = new Product("Mermelada casera", "mermelada.jpg", "link", "Frutihouse", false);
        ProductANMAT match = new ProductANMAT("Frutihouse", "Mermelada casera", "Mermelada casera", "Alimento", "VIGENTE");

        when(mlClient.searchProducts(any())).thenReturn(List.of(mockProduct));
        when(anmatService.filterByMatchPercentage(anyString(), anyString())).thenReturn(List.of(match));

        List<Product> result = searchService.searchRandomProduct();

        assertEquals(1, result.size());
        assertEquals("Mermelada casera", result.get(0).getTitle());
        assertTrue(result.get(0).isCertificate());
    }
}
