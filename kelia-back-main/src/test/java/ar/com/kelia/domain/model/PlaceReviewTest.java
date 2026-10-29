package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceReviewTest {

    @Test
    void shouldCreateReviewWithFields() {
        PlaceReview review = new PlaceReview();
        review.setAuthorName("María Gómez");
        review.setRating(5);
        review.setText("Excelente atención y muy buen ambiente.");
        review.setRelativeTimeDescription("Hace 3 días");

        assertEquals("María Gómez", review.getAuthorName());
        assertEquals(5, review.getRating());
        assertEquals("Excelente atención y muy buen ambiente.", review.getText());
        assertEquals("Hace 3 días", review.getRelativeTimeDescription());
    }

    @Test
    void shouldCreateEmptyReviewWithDefaultValues() {
        PlaceReview review = new PlaceReview();

        assertNull(review.getAuthorName());
        assertNull(review.getText());
        assertNull(review.getRelativeTimeDescription());
        assertEquals(0, review.getRating()); // int por defecto = 0
    }
}
