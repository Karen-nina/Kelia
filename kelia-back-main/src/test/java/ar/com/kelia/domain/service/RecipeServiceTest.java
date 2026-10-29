package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.repository.FavoriteRepository;
import ar.com.kelia.domain.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    FavoriteRepository favoriteRepository;

    @InjectMocks
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteRecipeById() {
        recipeService.delete("rec123");
        verify(recipeRepository).deleteById("rec123");
    }

    @Test
    void shouldGetRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId("rec456");

        when(recipeRepository.findById("rec456")).thenReturn(Optional.of(recipe));
        Recipe result = recipeService.getRecipeById("rec456");

        assertEquals("rec456", result.getId());
    }

    @Test
    void shouldReturnRecipesFromTerm() {
        List<Recipe> recipes = List.of(new Recipe(), new Recipe());
        when(recipeRepository.findByKeywordInIngredientsOrTitle("pollo", 3)).thenReturn(recipes);

        List<Recipe> result = recipeService.getRecipesFromTerm("pollo", 3);
        assertEquals(2, result.size());
    }

    @Test
    void shouldAddRecipeToFavoritesIfNotPresent() {
        String uid = "user001";
        String recipeId = "rec777";
        Favorite favorite = new Favorite(null, new ArrayList<>(), uid);

        when(favoriteRepository.findByUid(uid)).thenReturn(Optional.of(favorite));
        when(favoriteRepository.save(recipeId, uid)).thenReturn(favorite);

        Favorite result = recipeService.addRecipeToFavorites(recipeId, uid);

        assertTrue(result.getRecipes().contains(recipeId));
        verify(favoriteRepository).save(recipeId, uid);
    }

    @Test
    void shouldGetFavoriteRecipesFromUser() {
        String uid = "user123";
        Favorite favorite = new Favorite(null, List.of("rec1", "rec2"), uid);

        when(favoriteRepository.getFavoriteRecipeFromUid(uid)).thenReturn(Optional.of(favorite));
        Optional<Favorite> result = recipeService.getFavoriteRecipesFromUser(uid);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getRecipes().size());
    }

    @Test
    void shouldSearchByFilterRecent() {
        List<Recipe> rawRecipes = List.of(new Recipe(), new Recipe(), new Recipe());
        when(recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))).thenReturn(rawRecipes);

        List<Recipe> result = recipeService.searchByFilter("recent", 2);
        assertEquals(2, result.size());
    }

    // Agregá más tests para searchByFilter con otros filtros ("old", "rating", "default") si lo necesitás
}
