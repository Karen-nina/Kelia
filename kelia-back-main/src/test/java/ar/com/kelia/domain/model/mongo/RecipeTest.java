package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.model.mongo.Review;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void shouldCreateRecipeWithAllFields() {
        User user = new User();
        user.setUid("USR123");
        user.setUsername("chefmaster");

        Review review = new Review();
        review.setRating(5);
        review.setComment("¡Deliciosa!");

        Recipe recipe = new Recipe();
        recipe.setId("REC001");
        recipe.setTitle("Empanadas Salteñas");
        recipe.setCategory("Comida Regional");
        recipe.setUser(user);
        recipe.setIngredients(List.of("Carne", "Cebolla", "Comino"));
        recipe.setThumbnail("empanadas.jpg");
        recipe.setInstructions(List.of("Preparar masa", "Rellenar", "Hornear"));
        recipe.setReviews(List.of(review));
        recipe.setPreparationTime(90);
        recipe.setSave_count(1200);
        recipe.setTags(List.of("tradicional", "argentina"));
        recipe.setCreatedAt(LocalDateTime.of(2023, 5, 20, 12, 0));
        recipe.setRating(4.9); // @Transient

        assertEquals("REC001", recipe.getId());
        assertEquals("Empanadas Salteñas", recipe.getTitle());
        assertEquals("Comida Regional", recipe.getCategory());
        assertEquals("USR123", recipe.getUser().getUid());
        assertEquals(3, recipe.getIngredients().size());
        assertEquals("empanadas.jpg", recipe.getThumbnail());
        assertEquals(3, recipe.getInstructions().size());
        assertEquals(1, recipe.getReviews().size());
        assertEquals(90, recipe.getPreparationTime());
        assertEquals(1200, recipe.getSave_count());
        assertEquals("tradicional", recipe.getTags().get(0));
        assertEquals(LocalDateTime.of(2023, 5, 20, 12, 0), recipe.getCreatedAt());
        assertEquals(4.9, recipe.getRating());
    }

    @Test
    void shouldCreateEmptyRecipe() {
        Recipe recipe = new Recipe();

        assertNull(recipe.getId());
        assertNull(recipe.getTitle());
        assertNull(recipe.getCategory());
        assertNull(recipe.getUser());
        assertNull(recipe.getIngredients());
        assertNull(recipe.getThumbnail());
        assertNull(recipe.getInstructions());
        assertNull(recipe.getReviews());
        assertEquals(0, recipe.getPreparationTime());
        assertEquals(0, recipe.getSave_count());
        assertNull(recipe.getTags());
        assertNull(recipe.getCreatedAt());
        assertEquals(0.0, recipe.getRating()); // valor inicial de double
    }
}
