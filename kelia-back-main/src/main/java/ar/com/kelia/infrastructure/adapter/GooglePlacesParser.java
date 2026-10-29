package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.model.PlaceDetail;
import ar.com.kelia.domain.model.PlaceReview;
import ar.com.kelia.infrastructure.dto.PlaceDetailDTO;
import ar.com.kelia.infrastructure.dto.PlaceReviewDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GooglePlacesParser {

    public PlaceDetail parse(JSONObject result) {
        PlaceDetail dto = new PlaceDetail();

        dto.setName(result.optString("name"));
        dto.setAddress(result.optString("formatted_address"));
        dto.setPhoneNumber(result.optString("formatted_phone_number"));
        dto.setWebsite(result.optString("website"));
        dto.setRating(result.optDouble("rating", 0.0));
        dto.setTotalUserRatings(result.optInt("user_ratings_total", 0));

        if (result.has("geometry")) {
            JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
            dto.setLatitude(location.optDouble("lat", 0.0));
            dto.setLongitude(location.optDouble("lng", 0.0));
        }

        if (result.has("opening_hours")) {
            JSONArray weekdays = result.getJSONObject("opening_hours").optJSONArray("weekday_text");
            if (weekdays != null) {
                List<String> hours = new ArrayList<>();
                for (int i = 0; i < weekdays.length(); i++) {
                    hours.add(weekdays.getString(i));
                }
                dto.setOpeningHours(hours);
            }
        }

        if (result.has("reviews")) {
            List<PlaceReview> reviews = new ArrayList<>();
            JSONArray reviewArray = result.getJSONArray("reviews");
            for (int i = 0; i < reviewArray.length(); i++) {
                JSONObject r = reviewArray.getJSONObject(i);
                PlaceReview review = new PlaceReview();
                review.setAuthorName(r.optString("author_name"));
                review.setRating(r.optInt("rating"));
                review.setText(r.optString("text"));
                review.setRelativeTimeDescription(r.optString("relative_time_description"));
                reviews.add(review);
            }
            dto.setReviews(reviews);
        }

        return dto;
    }
}