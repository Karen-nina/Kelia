package ar.com.kelia.infrastructure.dto.mongo;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.infrastructure.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDTO {
    private String id;
    private String title;
    private UserDTO user;
    private List<String> ingredients;
    private String thumbnail;
    private List<String> instructions;
    private List<ReviewDTO> reviews;
    private String category;
    private int preparationTime;
    private int save_count;
    private double rating;
    private List<String> tags;
    private LocalDateTime createdAt;

    public RecipeDTO(String id, String title,String category, UserDTO user, List<String> ingredients, String thumbnail, List<String> instructions, List<ReviewDTO> reviews, int preparationTime, int save_count, double rating, List<String> tags, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.user = user;
        this.ingredients = ingredients;
        this.thumbnail = thumbnail;
        this.instructions = instructions;
        this.reviews = reviews;
        this.createdAt = createdAt;
        this.preparationTime = preparationTime;
        this.save_count = save_count;
        this.rating = rating;
        this.tags = tags;
    }
}
