package ar.com.kelia.domain.repository;

import ar.com.kelia.domain.model.mongo.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {
    void deleteById(String id);
    Optional<Recipe> findById(String id);
    List<Recipe> findByKeywordInIngredientsOrTitle(String term, int limit);
    List<Recipe> findAll(Sort sort);
    public List<Recipe> findAllWithQuery(int q);
    List<Recipe> findByKeywordInCategory(String category, int limit);
    List<Recipe> findByCategoryAndKeyword(String category, String term, int limit);
    void save(Recipe recipe);
}
