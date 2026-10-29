package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.model.PlaceDetail;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GooglePlacesParserTest {
    @Test
    void testParseFullJson() throws Exception {
        JSONObject json = new JSONObject()
                .put("name", "Test Place")
                .put("formatted_address", "123 Main St")
                .put("formatted_phone_number", "123-456-7890")
                .put("website", "http://test.com")
                .put("rating", 4.5)
                .put("user_ratings_total", 100)
                .put("geometry", new JSONObject().put("location", new JSONObject().put("lat", 10.0).put("lng", 20.0)))
                .put("opening_hours",
                        new JSONObject().put("weekday_text", new JSONArray(List.of("Mon: 9-5", "Tue: 9-5"))))
                .put("reviews", new JSONArray(List.of(
                        new JSONObject().put("author_name", "Ana").put("rating", 5).put("text", "Excelente")
                                .put("relative_time_description", "hoy"),
                        new JSONObject().put("author_name", "Luis").put("rating", 4).put("text", "Bueno")
                                .put("relative_time_description", "ayer"))));
        GooglePlacesParser parser = new GooglePlacesParser();
        PlaceDetail detail = parser.parse(json);
        assertEquals("Test Place", detail.getName());
        assertEquals("123 Main St", detail.getAddress());
        assertEquals("123-456-7890", detail.getPhoneNumber());
        assertEquals("http://test.com", detail.getWebsite());
        assertEquals(4.5, detail.getRating());
        assertEquals(100, detail.getTotalUserRatings());
        assertEquals(10.0, detail.getLatitude());
        assertEquals(20.0, detail.getLongitude());
        assertEquals(List.of("Mon: 9-5", "Tue: 9-5"), detail.getOpeningHours());
        assertEquals(2, detail.getReviews().size());
        assertEquals("Ana", detail.getReviews().get(0).getAuthorName());
        assertEquals(5, detail.getReviews().get(0).getRating());
        assertEquals("Excelente", detail.getReviews().get(0).getText());
        assertEquals("hoy", detail.getReviews().get(0).getRelativeTimeDescription());
    }

    @Test
    void testParsePartialJson() throws Exception {
        JSONObject json = new JSONObject().put("name", "Only Name");
        GooglePlacesParser parser = new GooglePlacesParser();
        PlaceDetail detail = parser.parse(json);
        assertEquals("Only Name", detail.getName());
        assertTrue(detail.getAddress() == null || detail.getAddress().isEmpty());
        assertTrue(detail.getPhoneNumber() == null || detail.getPhoneNumber().isEmpty());
        assertTrue(detail.getWebsite() == null || detail.getWebsite().isEmpty());
        assertEquals(0.0, detail.getRating());
        assertEquals(0, detail.getTotalUserRatings());
        assertEquals(0.0, detail.getLatitude());
        assertEquals(0.0, detail.getLongitude());
        assertTrue(detail.getOpeningHours() == null || detail.getOpeningHours().isEmpty());
        assertTrue(detail.getReviews() == null || detail.getReviews().isEmpty());
    }

    @Test
    void testParseEmptyJson() throws Exception {
        JSONObject json = new JSONObject();
        GooglePlacesParser parser = new GooglePlacesParser();
        PlaceDetail detail = parser.parse(json);
        assertTrue(detail.getName() == null || detail.getName().isEmpty());
        assertTrue(detail.getAddress() == null || detail.getAddress().isEmpty());
        assertTrue(detail.getPhoneNumber() == null || detail.getPhoneNumber().isEmpty());
        assertTrue(detail.getWebsite() == null || detail.getWebsite().isEmpty());
        assertEquals(0.0, detail.getRating());
        assertEquals(0, detail.getTotalUserRatings());
        assertEquals(0.0, detail.getLatitude());
        assertEquals(0.0, detail.getLongitude());
        assertTrue(detail.getOpeningHours() == null || detail.getOpeningHours().isEmpty());
        assertTrue(detail.getReviews() == null || detail.getReviews().isEmpty());
    }
}