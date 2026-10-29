package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.domain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetFavoriteRecipeByUidUseCase {
    @Autowired
    private RecipeService service;

    public Optional<Favorite> execute(String uid){
        return this.service.getFavoriteRecipesFromUser(uid);
    }
}
