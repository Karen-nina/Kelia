package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.model.mongo.Review;
import ar.com.kelia.infrastructure.dto.mongo.PlaceDTO;
import java.util.List;

public class PlaceMapper {

    public static PlaceDTO toDto(Place place) {
        if (place == null) {
            return null;
        }

        double averageRanking = 0.0;
        List<Review> reviews = place.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            averageRanking = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
        }

        return new PlaceDTO(
                place.getId(),
                place.getName(),
                place.getCategory(),
                place.getLat(),
                place.getLng(),
                place.getPlace_id(),
                averageRanking,
                place.getReviews()
        );
    }
}


