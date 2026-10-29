package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteRecipeUseCase {
    @Autowired
    private RecipeService service;

    public void execute(String id){
        this.service.delete(id);
    }

}
