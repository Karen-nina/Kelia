package ar.com.kelia.infrastructure.dto.mongo;
import ar.com.kelia.domain.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeCreatedDTO {
    private String title;
    private String uid;
    private List<String> ingredients;
    private String thumbnail;
    private List<String> instructions;
    private String category;
    private int preparationTime;
    private List<String> tags;
    private LocalDateTime createdAt;

    public RecipeCreatedDTO( String title, String uid, String category,  List<String> ingredients, String thumbnail, List<String> instructions, int preparationTime, List<String> tags, LocalDateTime createdAt) {
        this.title = title;
        this.uid  = uid;
        this.category = category;
        this.ingredients = ingredients;
        this.thumbnail = thumbnail;
        this.instructions = instructions;
        this.createdAt = createdAt;
        this.preparationTime = preparationTime;
        this.tags = tags;
    }
}

