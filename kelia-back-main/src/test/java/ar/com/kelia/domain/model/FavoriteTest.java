package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteTest {

    @Test
    void shouldCreateFavoriteWithConstructor() {
        List<String> recipeIds = List.of("RCP001", "RCP002");
        String uid = "user123";

        Favorite favorite = new Favorite(1L, recipeIds, uid);

        assertEquals(1L, favorite.getId());
        assertEquals(List.of("RCP001", "RCP002"), favorite.getRecipes());
        assertEquals("user123", favorite.getUid());
    }

    @Test
    void shouldCreateEmptyFavorite() {
        Favorite favorite = new Favorite();

        assertNull(favorite.getId());
        assertNull(favorite.getRecipes());
        assertNull(favorite.getUid());
    }

    @Test
    void shouldSetAndGetFavoriteFields() {
        Favorite favorite = new Favorite();
        favorite.setId(10L);
        favorite.setRecipes(List.of("RCP999", "RCP888"));
        favorite.setUid("abcDEF");

        assertEquals(10L, favorite.getId());
        assertEquals(List.of("RCP999", "RCP888"), favorite.getRecipes());
        assertEquals("abcDEF", favorite.getUid());
    }
}
