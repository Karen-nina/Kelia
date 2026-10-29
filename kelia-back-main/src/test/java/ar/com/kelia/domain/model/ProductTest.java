package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWithAllFields() {
        Product product = new Product(
                "Laptop XYZ",
                "image.jpg",
                "http://example.com/product",
                "TechSeller",
                true
        );

        assertEquals("Laptop XYZ", product.getTitle());
        assertEquals("image.jpg", product.getThumbnail());
        assertEquals("http://example.com/product", product.getLink());
        assertEquals("TechSeller", product.getSeller());
        assertTrue(product.isCertificate());
    }

    @Test
    void shouldCreateEmptyProductWithDefaultConstructor() {
        Product product = new Product();

        assertNull(product.getTitle());
        assertNull(product.getThumbnail());
        assertNull(product.getLink());
        assertNull(product.getSeller());
        assertFalse(product.isCertificate()); // valor boolean por defecto
    }

    @Test
    void shouldAllowSettingAndGettingFields() {
        Product product = new Product();
        product.setTitle("Smartphone ABC");
        product.setThumbnail("thumb.png");
        product.setLink("http://store.com/smartphone");
        product.setSeller("MobileCorp");
        product.setCertificate(false);

        assertEquals("Smartphone ABC", product.getTitle());
        assertEquals("thumb.png", product.getThumbnail());
        assertEquals("http://store.com/smartphone", product.getLink());
        assertEquals("MobileCorp", product.getSeller());
        assertFalse(product.isCertificate());
    }
}
