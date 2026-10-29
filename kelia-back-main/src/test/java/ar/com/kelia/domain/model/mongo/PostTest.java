package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.*;
import ar.com.kelia.domain.model.mongo.Comment;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.model.mongo.UserMongo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void shouldCreatePostWithAllFields() {
        User user = new User();
        user.setUid("USR001");

        UserMongo userMongo = new UserMongo();
        userMongo.setUid("USR001");

        MentionedRecipe recipe = new MentionedRecipe();
        recipe.setId("RCP001");
        recipe.setTitle("Milanesa");

        MentionedProduct product = new MentionedProduct();
        product.setId("PRD001");
        product.setTitle("Aceite de oliva");

        MentionedPlace place = new MentionedPlace();
        place.setId("PLC001");
        place.setTitle("Mercado Central");

        Comment comment = new Comment();
        comment.setContent("¡Qué buena receta!");
        comment.setUser(userMongo);

        Post post = new Post();
        post.setId("POST001");
        post.setUser(user);
        post.setContent("Hoy cociné milanesa con ensalada 😋");
        post.setCreatedAt(LocalDateTime.of(2023, 6, 15, 14, 0));
        post.setActive(1);
        post.setLikes(List.of("USR002", "USR003"));
        post.setComments(List.of(comment));
        post.setMentionedRecipes(List.of(recipe));
        post.setMentionedProducts(List.of(product));
        post.setMentionedPlaces(List.of(place));

        assertEquals("POST001", post.getId());
        assertEquals("USR001", post.getUser().getUid());
        assertEquals("Hoy cociné milanesa con ensalada 😋", post.getContent());
        assertEquals(1, post.getActive());
        assertEquals(2, post.getLikes().size());
        assertEquals("¡Qué buena receta!", post.getComments().get(0).getContent());
        assertEquals("Milanesa", post.getMentionedRecipes().get(0).getTitle());
        assertEquals("Aceite de oliva", post.getMentionedProducts().get(0).getTitle());
        assertEquals("Mercado Central", post.getMentionedPlaces().get(0).getTitle());
        assertEquals(LocalDateTime.of(2023, 6, 15, 14, 0), post.getCreatedAt());
    }

    @Test
    void shouldCreateEmptyPostWithDefaults() {
        Post post = new Post();

        assertNull(post.getId());
        assertNull(post.getUser());
        assertNull(post.getContent());
        assertNull(post.getCreatedAt());
        assertEquals(0, post.getActive());
        assertNull(post.getLikes());
        assertNull(post.getComments());
        assertNull(post.getMentionedRecipes());
        assertNull(post.getMentionedProducts());
        assertNull(post.getMentionedPlaces());
    }
}
