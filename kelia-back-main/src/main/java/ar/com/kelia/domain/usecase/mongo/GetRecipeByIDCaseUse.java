package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetRecipeByIDCaseUse {
    @Autowired
    private RecipeService service;

    public Recipe execute(String id){
        return this.service.getRecipeById(id);
    }
}

