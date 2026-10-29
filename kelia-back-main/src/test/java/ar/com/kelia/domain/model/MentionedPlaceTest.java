package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MentionedPlaceTest {

    @Test
    void shouldCreateMentionedPlaceAndSetValues() {
        MentionedPlace place = new MentionedPlace();
        place.setId("PLC001");
        place.setTitle("Parque Centenario");

        assertEquals("PLC001", place.getId());
        assertEquals("Parque Centenario", place.getTitle());
    }

    @Test
    void shouldCreateEmptyMentionedPlace() {
        MentionedPlace place = new MentionedPlace();

        assertNull(place.getId());
        assertNull(place.getTitle());
    }
}
