package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private String title;
    private String category;
    private User user;
    private List<String> ingredients;
    private String thumbnail;
    private List<String> instructions;
    private List<Review> reviews;
    private int preparationTime;
    private int save_count;
    private List<String> tags;
    private LocalDateTime createdAt;

    @Transient
    private double rating;
}
