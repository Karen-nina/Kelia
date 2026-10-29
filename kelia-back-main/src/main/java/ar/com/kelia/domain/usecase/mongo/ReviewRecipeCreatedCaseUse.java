package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewRecipeCreatedCaseUse {

    @Autowired
    ReviewService service;

    public void execute(String recipeId, String placeId, String uid, String text, int rating) {
        boolean isRecipeEmpty = recipeId == null || recipeId.isEmpty();
        boolean isPlaceEmpty = placeId == null || placeId.isEmpty();

        if (isRecipeEmpty && isPlaceEmpty) {
            throw new IllegalArgumentException("Debe especificar al menos recipeId o placeId");
        } else if (!isRecipeEmpty && !isPlaceEmpty) {
            throw new IllegalArgumentException("Debe especificar solo recipeId o placeId, no ambos");
        } else if (isRecipeEmpty) {
            this.service.reviewPlace(placeId, uid, text, rating);
        } else {
            this.service.reviewRecipe(recipeId, uid, text, rating);
        }
    }
}
