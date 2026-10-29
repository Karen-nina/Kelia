package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateRecipeUseCaseTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private CreateRecipeUseCase createRecipeUseCase;

    @Test
    void shouldCallSaveOnServiceWithCorrectRecipe() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setId("recipe-456");

        // Act
        createRecipeUseCase.execute(recipe);

        // Assert
        verify(recipeService).save(recipe);
    }
}
