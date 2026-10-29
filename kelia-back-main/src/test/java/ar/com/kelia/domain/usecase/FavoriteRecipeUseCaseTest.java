package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.domain.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteRecipeUseCaseTest {

    private RecipeService recipeService;
    private FavoriteRecipeUseCase favoriteRecipeUseCase;

    @BeforeEach
    void setUp() throws Exception {
        recipeService = mock(RecipeService.class);
        favoriteRecipeUseCase = new FavoriteRecipeUseCase();

        // Use reflection to set the private service field
        Field serviceField = FavoriteRecipeUseCase.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(favoriteRecipeUseCase, recipeService);
    }

    @Test
    void testExecuteWithValidRecipeAndUser() {
        // Arrange
        String recipeId = "recipe123";
        String userId = "user456";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);
        expectedFavorite.setRecipes(Arrays.asList(recipeId));

        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        Favorite result = favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFavorite, result);
        assertEquals(userId, result.getUid());
        assertTrue(result.getRecipes().contains(recipeId));
        verify(recipeService).addRecipeToFavorites(recipeId, userId);
    }

    @Test
    void testExecuteWithDifferentRecipeAndUser() {
        // Arrange
        String recipeId = "recipe789";
        String userId = "user123";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);
        expectedFavorite.setRecipes(Arrays.asList(recipeId));

        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        Favorite result = favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFavorite, result);
        assertEquals(userId, result.getUid());
        assertTrue(result.getRecipes().contains(recipeId));
        verify(recipeService).addRecipeToFavorites(recipeId, userId);
    }

    @Test
    void testExecuteWithNullRecipeId() {
        // Arrange
        String recipeId = null;
        String userId = "user456";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);

        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        Favorite result = favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFavorite, result);
        verify(recipeService).addRecipeToFavorites(recipeId, userId);
    }

    @Test
    void testExecuteWithNullUserId() {
        // Arrange
        String recipeId = "recipe123";
        String userId = null;
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);

        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        Favorite result = favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFavorite, result);
        verify(recipeService).addRecipeToFavorites(recipeId, userId);
    }

    @Test
    void testExecuteWithEmptyRecipeId() {
        // Arrange
        String recipeId = "";
        String userId = "user456";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);

        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        Favorite result = favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFavorite, result);
        verify(recipeService).addRecipeToFavorites(recipeId, userId);
    }

    @Test
    void testExecuteWithEmptyUserId() {
        // Arrange
        String recipeId = "recipe123";
        String userId = "";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);

        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        Favorite result = favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFavorite, result);
        verify(recipeService).addRecipeToFavorites(recipeId, userId);
    }

    @Test
    void testExecuteCallsServiceOnce() {
        // Arrange
        String recipeId = "recipe123";
        String userId = "user456";
        Favorite expectedFavorite = new Favorite();
        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        verify(recipeService, times(1)).addRecipeToFavorites(recipeId, userId);
    }

    @Test
    void testExecuteWithSpecialCharacters() {
        // Arrange
        String recipeId = "recipe-123_456";
        String userId = "user-789_abc";
        Favorite expectedFavorite = new Favorite();
        expectedFavorite.setUid(userId);

        when(recipeService.addRecipeToFavorites(recipeId, userId)).thenReturn(expectedFavorite);

        // Act
        Favorite result = favoriteRecipeUseCase.execute(recipeId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFavorite, result);
        verify(recipeService).addRecipeToFavorites(recipeId, userId);
    }
}