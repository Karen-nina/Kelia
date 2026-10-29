package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.domain.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetFavoriteRecipeByUidUseCaseTest {

    private RecipeService recipeService;
    private GetFavoriteRecipeByUidUseCase getFavoriteRecipeByUidUseCase;

    @BeforeEach
    void setUp() throws Exception {
        recipeService = mock(RecipeService.class);
        getFavoriteRecipeByUidUseCase = new GetFavoriteRecipeByUidUseCase();

        // Use reflection to set the private service field
        Field serviceField = GetFavoriteRecipeByUidUseCase.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(getFavoriteRecipeByUidUseCase, recipeService);
    }

    @Test
    void testExecuteWithValidUserId() {
        // Arrange
        String userId = "user123";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);
        expectedFavorite.setRecipes(Arrays.asList("recipe1", "recipe2", "recipe3"));

        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.of(expectedFavorite));

        // Act
        Optional<Favorite> result = getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedFavorite, result.get());
        assertEquals(userId, result.get().getUid());
        assertEquals(3, result.get().getRecipes().size());
        verify(recipeService).getFavoriteRecipesFromUser(userId);
    }

    @Test
    void testExecuteWithUserHavingNoFavorites() {
        // Arrange
        String userId = "user456";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);
        expectedFavorite.setRecipes(Arrays.asList());

        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.of(expectedFavorite));

        // Act
        Optional<Favorite> result = getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedFavorite, result.get());
        assertEquals(userId, result.get().getUid());
        assertTrue(result.get().getRecipes().isEmpty());
        verify(recipeService).getFavoriteRecipesFromUser(userId);
    }

    @Test
    void testExecuteWithNonExistentUser() {
        // Arrange
        String userId = "nonexistent_user";
        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.empty());

        // Act
        Optional<Favorite> result = getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(recipeService).getFavoriteRecipesFromUser(userId);
    }

    @Test
    void testExecuteWithNullUserId() {
        // Arrange
        String userId = null;
        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.empty());

        // Act
        Optional<Favorite> result = getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(recipeService).getFavoriteRecipesFromUser(userId);
    }

    @Test
    void testExecuteWithEmptyUserId() {
        // Arrange
        String userId = "";
        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.empty());

        // Act
        Optional<Favorite> result = getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(recipeService).getFavoriteRecipesFromUser(userId);
    }

    @Test
    void testExecuteWithUserHavingManyFavorites() {
        // Arrange
        String userId = "user789";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);
        expectedFavorite.setRecipes(Arrays.asList(
                "recipe1", "recipe2", "recipe3", "recipe4", "recipe5",
                "recipe6", "recipe7", "recipe8", "recipe9", "recipe10"));

        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.of(expectedFavorite));

        // Act
        Optional<Favorite> result = getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedFavorite, result.get());
        assertEquals(userId, result.get().getUid());
        assertEquals(10, result.get().getRecipes().size());
        verify(recipeService).getFavoriteRecipesFromUser(userId);
    }

    @Test
    void testExecuteCallsServiceOnce() {
        // Arrange
        String userId = "user123";
        Favorite expectedFavorite = new Favorite();
        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.of(expectedFavorite));

        // Act
        getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        verify(recipeService, times(1)).getFavoriteRecipesFromUser(userId);
    }

    @Test
    void testExecuteWithSpecialCharacters() {
        // Arrange
        String userId = "user-123_456";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);
        expectedFavorite.setRecipes(Arrays.asList("recipe-abc", "recipe-def"));

        when(recipeService.getFavoriteRecipesFromUser(userId)).thenReturn(Optional.of(expectedFavorite));

        // Act
        Optional<Favorite> result = getFavoriteRecipeByUidUseCase.execute(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedFavorite, result.get());
        assertEquals(userId, result.get().getUid());
        assertEquals(2, result.get().getRecipes().size());
        verify(recipeService).getFavoriteRecipesFromUser(userId);
    }
}