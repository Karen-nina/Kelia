package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRecipeByIDCaseUseTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private GetRecipeByIDCaseUse getRecipeByIDCaseUse;

    @Test
    void shouldCallServiceAndReturnRecipe() {
        // Arrange
        String recipeId = "recipe-123";
        Recipe expectedRecipe = new Recipe();
        expectedRecipe.setId(recipeId);

        when(recipeService.getRecipeById(recipeId)).thenReturn(expectedRecipe);

        // Act
        Recipe result = getRecipeByIDCaseUse.execute(recipeId);

        // Assert
        assertThat(result).isEqualTo(expectedRecipe);
        verify(recipeService).getRecipeById(recipeId);
    }
}
