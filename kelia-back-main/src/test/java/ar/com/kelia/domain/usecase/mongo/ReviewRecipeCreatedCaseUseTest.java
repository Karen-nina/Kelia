package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ReviewRecipeCreatedCaseUseTest {

    private ReviewService reviewService;
    private ReviewRecipeCreatedCaseUse caseUse;

    @BeforeEach
    void setUp() {
        reviewService = mock(ReviewService.class);
        caseUse = new ReviewRecipeCreatedCaseUse();
        // inyectamos el mock manualmente
        caseUse.service = reviewService;
    }

    @Test
    void shouldCallReviewPlaceWhenRecipeIdIsNull() {
        // Act
        caseUse.execute(null, "place-123", "uid-1", "Muy bueno", 5);

        // Assert
        verify(reviewService, times(1)).reviewPlace("place-123", "uid-1", "Muy bueno", 5);
        verify(reviewService, never()).reviewRecipe(any(), any(), any(), anyInt());
    }

    @Test
    void shouldCallReviewRecipeWhenPlaceIdIsNull() {
        // Act
        caseUse.execute("recipe-123", null, "uid-2", "Rico", 4);

        // Assert
        verify(reviewService, times(1)).reviewRecipe("recipe-123", "uid-2", "Rico", 4);
        verify(reviewService, never()).reviewPlace(any(), any(), any(), anyInt());
    }

    @Test
    void shouldThrowWhenBothIdsAreProvided() {
        // Assert
        assertThatThrownBy(() ->
                caseUse.execute("recipe-1", "place-1", "uid-3", "texto", 3)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Debe especificar solo recipeId o placeId");
    }

    @Test
    void shouldThrowWhenBothIdsAreNull() {
        // Assert
        assertThatThrownBy(() ->
                caseUse.execute(null, null, "uid-4", "texto", 2)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldCallReviewPlaceWhenRecipeIdIsEmpty() {
        caseUse.execute("", "place-456", "uid-5", "Bien", 4);

        verify(reviewService).reviewPlace("place-456", "uid-5", "Bien", 4);
    }

    @Test
    void shouldCallReviewRecipeWhenPlaceIdIsEmpty() {
        caseUse.execute("recipe-789", "", "uid-6", "Excelente", 5);

        verify(reviewService).reviewRecipe("recipe-789", "uid-6", "Excelente", 5);
    }
}
