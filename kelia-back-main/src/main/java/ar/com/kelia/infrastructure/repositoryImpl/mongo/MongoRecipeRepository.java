package ar.com.kelia.infrastructure.repositoryImpl.mongo;

import ar.com.kelia.domain.model.mongo.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoRecipeRepository extends MongoRepository<Recipe, String> {

    @Query("{ '$or': [ { 'ingredients': { $regex: ?0, $options: 'i' } }, { 'title': { $regex: ?0, $options: 'i' } } ] }")
    List<Recipe> findByKeywordInIngredientsOrTitle(String key, Pageable pageable);

    @Query("{ 'category': { $regex: ?0, $options: 'i' } }")
    List<Recipe> findByKeywordInCategory(String key, Pageable pageable);

    @Query("{ 'category': ?0, '$or': [ { 'ingredients': { $regex: ?1, $options: 'i' } }, { 'title': { $regex: ?1, $options: 'i' } } ] }")
    List<Recipe> findByCategoryAndKeyword(String category, String term, Pageable pageable);

    @Query("{}")
    List<Recipe> findAllWithQuery(Pageable pageable);


}
