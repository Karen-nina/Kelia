package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.repository.RecipeRepository;
import ar.com.kelia.infrastructure.repositoryImpl.mongo.MongoRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecipeRepositoryAdapter implements RecipeRepository {

    private final MongoRecipeRepository mongoRepository;

    @Autowired
    public RecipeRepositoryAdapter(MongoRecipeRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public Optional<Recipe> findById(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public List<Recipe> findByKeywordInIngredientsOrTitle(String term, int q) {
        Pageable limit = PageRequest.of(0, q);
        return mongoRepository.findByKeywordInIngredientsOrTitle(term, limit);
    }

    @Override
    public List<Recipe> findAll(Sort sort) {
        return this.mongoRepository.findAll(sort);
    }

    @Override
    public List<Recipe> findAllWithQuery(int q) {
        Pageable limit = PageRequest.of(0, q);
        return mongoRepository.findAllWithQuery(limit);
    }

    @Override
    public List<Recipe> findByKeywordInCategory(String category, int q) {
        Pageable limit = PageRequest.of(0, q);
        return mongoRepository.findByKeywordInCategory(category, limit);
    }

    @Override
    public List<Recipe> findByCategoryAndKeyword(String category, String term,int q) {
        Pageable limit = PageRequest.of(0, q);
        return mongoRepository.findByCategoryAndKeyword(category, term, limit);
    }

    @Override
    public void save(Recipe recipe) {
        mongoRepository.save(recipe);
    }
}

