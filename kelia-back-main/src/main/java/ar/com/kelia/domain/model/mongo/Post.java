package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.MentionedPlace;
import ar.com.kelia.domain.model.MentionedProduct;
import ar.com.kelia.domain.model.MentionedRecipe;
import ar.com.kelia.domain.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "posts")
@Data
public class Post {
    @Id
    private String id;
    private User user;
    private String content;
    private LocalDateTime createdAt;
    private int active;
    private List<String> likes;
    private List<Comment> comments;
    private List<MentionedRecipe> mentionedRecipes;
    private List<MentionedProduct> mentionedProducts;
    private List<MentionedPlace> mentionedPlaces;
}