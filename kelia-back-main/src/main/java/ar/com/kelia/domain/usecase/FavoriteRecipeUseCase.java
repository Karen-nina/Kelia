package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.domain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteRecipeUseCase {
    @Autowired
    private RecipeService service;

    public Favorite execute(String recipeId, String uid){
       return this.service.addRecipeToFavorites(recipeId,uid);
    }
}
