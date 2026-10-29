package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.model.mongo.Review;
import ar.com.kelia.infrastructure.dto.mongo.PlaceDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaceMapperTest {

    @Test
    void testToDto() {
        Place place = new Place();
        place.setId("p1");
        place.setName("Test Place");
        place.setCategory("Restaurant");
        place.setLat(-34.6);
        place.setLng(-58.4);
        place.setPlace_id("pid123");

        Review r1 = new Review();
        r1.setRating(4);
        Review r2 = new Review();
        r2.setRating(2);
        place.setReviews(List.of(r1, r2));

        PlaceDTO dto = PlaceMapper.toDto(place);

        assertNotNull(dto);
        assertEquals("p1", dto.getId());
        assertEquals("Test Place", dto.getName());
        assertEquals("Restaurant", dto.getCategory());
        assertEquals(-34.6, dto.getLat());
        assertEquals(-58.4, dto.getLng());
        assertEquals("pid123", dto.getPlace_id());
        assertEquals(2, dto.getReviews().size());
    }

    @Test
    void testToDtoWithNoReviews() {
        Place place = new Place();
        place.setId("p2");
        place.setName("Empty Place");

        PlaceDTO dto = PlaceMapper.toDto(place);

        assertNotNull(dto);
        assertEquals("p2", dto.getId());
    }
}
