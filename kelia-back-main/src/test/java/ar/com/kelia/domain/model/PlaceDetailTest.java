package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaceDetailTest {

    @Test
    void shouldCreatePlaceDetailWithAllFields() {
        PlaceReview review = new PlaceReview();
        review.setAuthorName("Laura");
        review.setRating(4);
        review.setText("Muy buena comida");
        review.setRelativeTimeDescription("Hace 1 semana");

        PlaceDetail place = new PlaceDetail();
        place.setName("Restaurante Don Julio");
        place.setAddress("Guatemala 4691, CABA");
        place.setPhoneNumber("+54 11 4831-9564");
        place.setWebsite("http://donjulio.com");
        place.setRating(4.7);
        place.setTotalUserRatings(1524);
        place.setLatitude(-34.5884);
        place.setLongitude(-58.4255);
        place.setOpeningHours(List.of("Lunes a Domingo: 12:00 - 23:00"));
        place.setReviews(List.of(review));

        assertEquals("Restaurante Don Julio", place.getName());
        assertEquals("Guatemala 4691, CABA", place.getAddress());
        assertEquals("+54 11 4831-9564", place.getPhoneNumber());
        assertEquals("http://donjulio.com", place.getWebsite());
        assertEquals(4.7, place.getRating());
        assertEquals(1524, place.getTotalUserRatings());
        assertEquals(-34.5884, place.getLatitude());
        assertEquals(-58.4255, place.getLongitude());
        assertEquals(1, place.getOpeningHours().size());
        assertEquals("Lunes a Domingo: 12:00 - 23:00", place.getOpeningHours().get(0));
        assertEquals(1, place.getReviews().size());
        assertEquals("Laura", place.getReviews().get(0).getAuthorName());
    }

    @Test
    void shouldCreateEmptyPlaceDetail() {
        PlaceDetail place = new PlaceDetail();

        assertNull(place.getName());
        assertNull(place.getAddress());
        assertNull(place.getPhoneNumber());
        assertNull(place.getWebsite());
        assertEquals(0.0, place.getRating());
        assertEquals(0, place.getTotalUserRatings());
        assertEquals(0.0, place.getLatitude());
        assertEquals(0.0, place.getLongitude());
        assertNull(place.getOpeningHours());
        assertNull(place.getReviews());
    }
}
