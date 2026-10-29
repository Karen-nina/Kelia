package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.Favorite;
import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.repository.FavoriteRepository;
import ar.com.kelia.domain.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository repository;

    @Autowired
    FavoriteRepository favoriteRepository;

    public void delete(String id) {
        this.repository.deleteById(id);
    }

    public Recipe getRecipeById(String id) {
        Optional<Recipe> recipe = this.repository.findById(id);
        return recipe.get();
    }

    public List<Recipe> getRecipesFromTerm(String term, int q) {
        return this.repository.findByKeywordInIngredientsOrTitle(term,q);
    }

    public List<Recipe> getAll(int q) {
        return this.repository.findAllWithQuery(q);
    }

    public List<Recipe> getRecipesFromCategory(String category, int q) {
        return this.repository.findByKeywordInCategory(category,q);
    }

    public List<Recipe> searchByFilter(String filter, int limit) {
        return switch (filter) {
            case "recent" -> repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                    .stream()
                    .limit(limit)
                    .toList();
            case "old" -> repository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"))
                    .stream()
                    .limit(limit)
                    .toList();
            case "rating" -> repository.findAll(Sort.by(Sort.Direction.DESC, "rating"))
                    .stream()
                    .limit(limit)
                    .toList();
            default -> repository.findAllWithQuery(10)
                    .stream()
                    .limit(limit)
                    .toList();
        };
    }

    public List<Recipe> getRecipesFromCategoryAndTerm(String category, String term, int q) {
        return this.repository.findByCategoryAndKeyword(category, term, q);
    }

    public void save(Recipe recipe){
        this.repository.save(recipe);
    }

    public Favorite addRecipeToFavorites(String recipeId, String userUid) {
        Favorite favorite = favoriteRepository.findByUid(userUid)
                .orElse(new Favorite(null, new ArrayList<>(), userUid));

        if (!favorite.getRecipes().contains(recipeId)) {
            favorite.getRecipes().add(recipeId);
        }

        return favoriteRepository.save(recipeId, userUid);
    }

    public Optional<Favorite> getFavoriteRecipesFromUser(String uid){
        return this.favoriteRepository.getFavoriteRecipeFromUid(uid);
    }
}
