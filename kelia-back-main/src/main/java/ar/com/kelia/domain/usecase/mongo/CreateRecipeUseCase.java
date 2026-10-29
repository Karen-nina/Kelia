package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRecipeUseCase {
    @Autowired
    private RecipeService service;

    public void execute(Recipe recipe){
        this.service.save(recipe);
    }
}
