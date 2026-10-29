package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MentionedProductTest {

    @Test
    void shouldCreateMentionedProductAndSetValues() {
        MentionedProduct product = new MentionedProduct();
        product.setId("PRD001");
        product.setTitle("Yerba mate con palo");

        assertEquals("PRD001", product.getId());
        assertEquals("Yerba mate con palo", product.getTitle());
    }

    @Test
    void shouldCreateEmptyMentionedProduct() {
        MentionedProduct product = new MentionedProduct();

        assertNull(product.getId());
        assertNull(product.getTitle());
    }
}
