package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteRecipeUseCaseTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private DeleteRecipeUseCase deleteRecipeUseCase;

    @Test
    void shouldCallDeleteOnServiceWithCorrectId() {
        // Arrange
        String recipeId = "recipe-123";

        // Act
        deleteRecipeUseCase.execute(recipeId);

        // Assert
        verify(recipeService).delete(recipeId);
    }
}
