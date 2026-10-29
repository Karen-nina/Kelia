package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRecipesUseCase {

    @Autowired
    private RecipeService service;

    public List<Recipe> execute(String term,String category,String filter,int limit) {
        List<Recipe> recipes;

        if (filter != null) {
            return this.service.searchByFilter(filter, limit);
        }
        if (term != null && category != null) {
            recipes = this.service.getRecipesFromCategoryAndTerm(category, term, limit);
        } else if (term != null) {
            recipes =  this.service.getRecipesFromTerm(term, limit);;
        } else if (category != null) {
            recipes = this.service.getRecipesFromCategory(category,limit);
        } else {
            recipes = this.service.getAll(limit);
        }
        return recipes;
    }
}

