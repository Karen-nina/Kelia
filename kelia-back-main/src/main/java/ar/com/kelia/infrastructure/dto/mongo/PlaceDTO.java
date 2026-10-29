package ar.com.kelia.infrastructure.dto.mongo;

import ar.com.kelia.domain.model.mongo.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private String id;
    private String name;
    private String category;
    private Double lat;
    private Double lng;
    private String place_id;
    private double rating;
    private List<Review> reviews;
}
