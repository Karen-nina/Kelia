package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MentionedRecipeTest {

    @Test
    void shouldCreateMentionedRecipeAndSetValues() {
        MentionedRecipe recipe = new MentionedRecipe();
        recipe.setId("RCP001");
        recipe.setTitle("Tarta de acelga");

        assertEquals("RCP001", recipe.getId());
        assertEquals("Tarta de acelga", recipe.getTitle());
    }

    @Test
    void shouldCreateEmptyMentionedRecipe() {
        MentionedRecipe recipe = new MentionedRecipe();

        assertNull(recipe.getId());
        assertNull(recipe.getTitle());
    }
}
