package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.model.mongo.UserMongo;
import org.junit.jupiter.api.Test;
import ar.com.kelia.domain.model.mongo.Review;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {

    @Test
    void shouldCreatePlaceAndSetValues() {
        Review review = new Review();
        UserMongo user = new UserMongo();
        user.setUid("USR100");
        user.setUsername("maria");
        review.setUser(user);
        review.setRating(4);
        review.setComment("Lugar hermoso para caminar");

        Place place = new Place();
        place.setId("PLC001");
        place.setName("Reserva Ecológica");
        place.setCategory("Parque");
        place.setLat(-34.6037);
        place.setLng(-58.3816);
        place.setPlace_id("ChIJdUyx15vKvJURQeoZpHZMbD0");
        place.setReviews(List.of(review));

        assertEquals("PLC001", place.getId());
        assertEquals("Reserva Ecológica", place.getName());
        assertEquals("Parque", place.getCategory());
        assertEquals(-34.6037, place.getLat());
        assertEquals(-58.3816, place.getLng());
        assertEquals("ChIJdUyx15vKvJURQeoZpHZMbD0", place.getPlace_id());
        assertEquals(1, place.getReviews().size());
        assertEquals("maria", place.getReviews().get(0).getUser().getUsername());
    }

    @Test
    void shouldCreateEmptyPlaceWithDefaults() {
        Place place = new Place();

        assertNull(place.getId());
        assertNull(place.getName());
        assertNull(place.getCategory());
        assertNull(place.getLat());
        assertNull(place.getLng());
        assertNull(place.getPlace_id());
        assertNull(place.getReviews());
    }
}
