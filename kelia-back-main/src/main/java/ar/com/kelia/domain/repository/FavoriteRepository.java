package ar.com.kelia.domain.repository;

import ar.com.kelia.domain.model.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository {
    Optional<Favorite> findByUid(String user);
    Favorite save(String recipeId, String uid);
    Optional<Favorite>  getFavoriteRecipeFromUid(String uid);
}
