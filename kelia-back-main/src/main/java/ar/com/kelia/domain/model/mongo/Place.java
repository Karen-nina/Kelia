package ar.com.kelia.domain.model.mongo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "places")
public class Place {
    @Id
    private String id;
    private String name;
    private String category;
    private Double lat;
    private Double lng;
    private String place_id;

    private List<Review> reviews;
}
