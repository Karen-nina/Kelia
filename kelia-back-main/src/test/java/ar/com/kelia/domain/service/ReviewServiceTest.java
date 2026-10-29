package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.*;
import ar.com.kelia.domain.repository.PlaceRepository;
import ar.com.kelia.domain.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    UserService userService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    PlaceRepository placeRepository;

    @InjectMocks
    ReviewService reviewService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddReviewToRecipeWhenUserAndRecipeExist() {
        String uid = "USR01";
        String recipeId = "RCP01";
        String comment = "Excelente";
        int rating = 5;

        User user = new User();
        user.setUid(uid);
        user.setUsername("lucia");
        user.setDisplayName("Lucía");
        user.setAvatar("lucia.jpg");

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setReviews(new ArrayList<>());

        when(userService.findByUid(uid)).thenReturn(Optional.of(user));
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        reviewService.reviewRecipe(recipeId, uid, comment, rating);

        assertEquals(1, recipe.getReviews().size());
        Review addedReview = recipe.getReviews().get(0);
        assertEquals(comment, addedReview.getComment());
        assertEquals(rating, addedReview.getRating());
        assertEquals(uid, addedReview.getUser().getUid());

        verify(recipeRepository).save(recipe);
    }

    @Test
    void shouldNotAddReviewIfRecipeNotFound() {
        String uid = "USR01";
        String recipeId = "NOT_FOUND";

        User user = new User();
        user.setUid(uid);

        when(userService.findByUid(uid)).thenReturn(Optional.of(user));
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        reviewService.reviewRecipe(recipeId, uid, "Comentario", 3);

        verify(recipeRepository, never()).save(any());
    }

    @Test
    void shouldAddReviewToPlaceWhenUserAndPlaceExist() {
        String uid = "USR02";
        String placeId = "PLC01";

        User user = new User();
        user.setUid(uid);
        user.setUsername("carlos");
        user.setDisplayName("Carlos");
        user.setAvatar("carlos.jpg");

        Place place = new Place();
        place.setId(placeId);
        place.setReviews(new ArrayList<>());

        when(userService.findByUid(uid)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        reviewService.reviewPlace(placeId, uid, "Muy lindo lugar", 4);

        assertEquals(1, place.getReviews().size());
        Review review = place.getReviews().get(0);
        assertEquals("Muy lindo lugar", review.getComment());
        assertEquals(4, review.getRating());
        assertEquals("Carlos", review.getUser().getDisplayName());

        verify(placeRepository).save(place);
    }

    @Test
    void shouldNotAddReviewIfUserNotFound() {
        when(userService.findByUid("INVALID")).thenReturn(Optional.empty());

        reviewService.reviewPlace("PLC999", "INVALID", "Texto", 2);

        verify(placeRepository, never()).findById(any());
        verify(placeRepository, never()).save(any());
    }
}
