package ar.com.kelia.infrastructure.dto.mongo;

import ar.com.kelia.domain.model.MentionedPlace;
import ar.com.kelia.domain.model.MentionedProduct;
import ar.com.kelia.domain.model.MentionedRecipe;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.time.ZoneId;

@Data
public class PostDTO {
    private String uid;
    private String content;
    private LocalDateTime createdAt;
    private int active;
    private List<MentionedRecipe> mentionedRecipes;
    private List<MentionedProduct> mentionedProducts;
    private List<MentionedPlace> mentionedPlaces;

    public PostDTO() {
        this.createdAt = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
    }
}
