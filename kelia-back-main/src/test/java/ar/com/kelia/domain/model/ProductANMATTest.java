package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductANMATTest {

    @Test
    void shouldCreateProductWithCustomConstructor() {
        ProductANMAT product = new ProductANMAT(
                "Laboratorio XY",
                "Ibuprofeno",
                "Ibuprofeno 600mg",
                "Medicamento",
                "Aprobado"
        );

        assertEquals("Laboratorio XY", product.getBrand());
        assertEquals("Ibuprofeno", product.getTradeName());
        assertEquals("Ibuprofeno 600mg", product.getSalesName());
        assertEquals("Medicamento", product.getProductType());
        assertEquals("Aprobado", product.getStatus());
        assertNull(product.getId()); // No asignado todavía
    }

    @Test
    void shouldCreateEmptyProductWithDefaultConstructor() {
        ProductANMAT product = new ProductANMAT();

        assertNull(product.getBrand());
        assertNull(product.getTradeName());
        assertNull(product.getSalesName());
        assertNull(product.getProductType());
        assertNull(product.getStatus());
        assertNull(product.getId());
    }

    @Test
    void shouldSetAndGetFields() {
        ProductANMAT product = new ProductANMAT();
        product.setBrand("LabCorp");
        product.setTradeName("Paracetamol");
        product.setSalesName("Paracetamol Forte");
        product.setProductType("Farmaco");
        product.setStatus("En revisión");

        assertEquals("LabCorp", product.getBrand());
        assertEquals("Paracetamol", product.getTradeName());
        assertEquals("Paracetamol Forte", product.getSalesName());
        assertEquals("Farmaco", product.getProductType());
        assertEquals("En revisión", product.getStatus());
    }
}
