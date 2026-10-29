package ar.com.kelia.infrastructure.dto;

import org.junit.jupiter.api.Test;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PlaceDetailDTOTest {
    @Test
    void testSettersAndGetters() {
        // Test básico: verificar que los setters y getters funcionan correctamente
        PlaceDetailDTO dto = new PlaceDetailDTO();
        dto.setName("name");
        dto.setAddress("address");
        dto.setPhoneNumber("123");
        dto.setWebsite("web");
        dto.setRating(4.5);
        dto.setTotalUserRatings(10);
        dto.setLatitude(1.1);
        dto.setLongitude(2.2);
        dto.setOpeningHours(Collections.singletonList("9-18"));
        dto.setReviews(Collections.emptyList());
        assertEquals("name", dto.getName());
        assertEquals("address", dto.getAddress());
        assertEquals("123", dto.getPhoneNumber());
        assertEquals("web", dto.getWebsite());
        assertEquals(4.5, dto.getRating());
        assertEquals(10, dto.getTotalUserRatings());
        assertEquals(1.1, dto.getLatitude());
        assertEquals(2.2, dto.getLongitude());
        assertNotNull(dto.getOpeningHours());
        assertNotNull(dto.getReviews());
    }

    @Test
    void testEqualsAndHashCode() {
        // Test de igualdad y hash code: verificar que dos DTOs vacíos son iguales
        PlaceDetailDTO dto1 = new PlaceDetailDTO();
        PlaceDetailDTO dto2 = new PlaceDetailDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }
}