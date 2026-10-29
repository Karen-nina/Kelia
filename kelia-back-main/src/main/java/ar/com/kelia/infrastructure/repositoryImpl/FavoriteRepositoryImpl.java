package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.domain.repository.FavoriteRepository;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JpaFavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {

    @Autowired
    JpaFavoriteRepository repository;

    @Override
    public Optional<Favorite> findByUid(String user) {
        return repository.findByUid(user);
    }

    @Override
    public Favorite save(String recipeId, String uid) {
        Optional<Favorite> optionalFavorite = repository.findByUid(uid);

        Favorite favorite;

        if (optionalFavorite.isPresent()) {
            favorite = optionalFavorite.get();
            if (!favorite.getRecipes().contains(recipeId)) {
                favorite.getRecipes().add(recipeId);
            }
        } else {
            favorite = new Favorite(null, new ArrayList<>(List.of(recipeId)), uid);
        }

        return repository.save(favorite);
    }

    @Override
    public Optional<Favorite> getFavoriteRecipeFromUid(String uid) {
        return this.repository.findByUid(uid);
    }
}
