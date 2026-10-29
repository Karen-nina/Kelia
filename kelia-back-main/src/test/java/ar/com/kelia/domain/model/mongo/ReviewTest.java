package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.mongo.Review;
import ar.com.kelia.domain.model.mongo.UserMongo;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    void shouldCreateReviewWithAllFields() {
        UserMongo user = new UserMongo();
        user.setUid("UID456");
        user.setDisplayName("Carlos");
        user.setUsername("carlos123");
        user.setAvatar("carlos.jpg");

        Review review = new Review(user, 5, "Excelente experiencia");

        assertEquals(user, review.getUser());
        assertEquals(5, review.getRating());
        assertEquals("Excelente experiencia", review.getComment());
        assertNotNull(review.getCreatedAt());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        assertTrue(review.getCreatedAt().isBefore(now.plusSeconds(1)));
        assertTrue(review.getCreatedAt().isAfter(now.minusSeconds(10))); // margen de seguridad
    }

    @Test
    void shouldCreateEmptyReview() {
        Review review = new Review();

        assertNull(review.getUser());
        assertEquals(0, review.getRating());
        assertNull(review.getComment());
        assertNotNull(review.getCreatedAt());
    }

    @Test
    void shouldAllowUpdatingFields() {
        Review review = new Review();
        UserMongo user = new UserMongo();
        user.setUid("XYZ999");

        review.setUser(user);
        review.setRating(3);
        review.setComment("Puede mejorar");
        review.setCreatedAt(LocalDateTime.of(2024, 12, 1, 10, 0));

        assertEquals(user, review.getUser());
        assertEquals(3, review.getRating());
        assertEquals("Puede mejorar", review.getComment());
        assertEquals(LocalDateTime.of(2024, 12, 1, 10, 0), review.getCreatedAt());
    }
}