package ar.com.kelia.infrastructure.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlaceReviewDTOTest {
    @Test
    void testSettersAndGetters() {
        // Test básico: verificar que los setters y getters funcionan correctamente
        PlaceReviewDTO dto = new PlaceReviewDTO();
        dto.setAuthorName("author");
        dto.setRating(5);
        dto.setText("texto");
        dto.setRelativeTimeDescription("ayer");
        assertEquals("author", dto.getAuthorName());
        assertEquals(5, dto.getRating());
        assertEquals("texto", dto.getText());
        assertEquals("ayer", dto.getRelativeTimeDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        // Test de igualdad y hash code: verificar que dos DTOs vacíos son iguales
        PlaceReviewDTO dto1 = new PlaceReviewDTO();
        PlaceReviewDTO dto2 = new PlaceReviewDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }
}