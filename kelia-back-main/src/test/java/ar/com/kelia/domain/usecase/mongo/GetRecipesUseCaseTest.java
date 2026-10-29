package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRecipesUseCaseTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private GetRecipesUseCase getRecipesUseCase;

    private List<Recipe> mockRecipes;

    @BeforeEach
    void setUp() {
        mockRecipes = List.of(new Recipe(), new Recipe());
    }

    @Test
    void shouldCallSearchByFilterWhenFilterIsNotNull() {
        when(recipeService.searchByFilter("popular", 10)).thenReturn(mockRecipes);

        List<Recipe> result = getRecipesUseCase.execute(null, null, "popular", 10);

        assertThat(result).isEqualTo(mockRecipes);
        verify(recipeService).searchByFilter("popular", 10);
        verifyNoMoreInteractions(recipeService);
    }

    @Test
    void shouldCallGetRecipesFromCategoryAndTermWhenTermAndCategoryAreNotNull() {
        when(recipeService.getRecipesFromCategoryAndTerm("postres", "chocolate", 5)).thenReturn(mockRecipes);

        List<Recipe> result = getRecipesUseCase.execute("chocolate", "postres", null, 5);

        assertThat(result).isEqualTo(mockRecipes);
        verify(recipeService).getRecipesFromCategoryAndTerm("postres", "chocolate", 5);
        verifyNoMoreInteractions(recipeService);
    }

    @Test
    void shouldCallGetRecipesFromTermWhenOnlyTermIsNotNull() {
        when(recipeService.getRecipesFromTerm("pizza", 8)).thenReturn(mockRecipes);

        List<Recipe> result = getRecipesUseCase.execute("pizza", null, null, 8);

        assertThat(result).isEqualTo(mockRecipes);
        verify(recipeService).getRecipesFromTerm("pizza", 8);
        verifyNoMoreInteractions(recipeService);
    }

    @Test
    void shouldCallGetRecipesFromCategoryWhenOnlyCategoryIsNotNull() {
        when(recipeService.getRecipesFromCategory("salado", 7)).thenReturn(mockRecipes);

        List<Recipe> result = getRecipesUseCase.execute(null, "salado", null, 7);

        assertThat(result).isEqualTo(mockRecipes);
        verify(recipeService).getRecipesFromCategory("salado", 7);
        verifyNoMoreInteractions(recipeService);
    }

    @Test
    void shouldCallGetAllWhenNoParamsProvided() {
        when(recipeService.getAll(3)).thenReturn(mockRecipes);

        List<Recipe> result = getRecipesUseCase.execute(null, null, null, 3);

        assertThat(result).isEqualTo(mockRecipes);
        verify(recipeService).getAll(3);
        verifyNoMoreInteractions(recipeService);
    }
}