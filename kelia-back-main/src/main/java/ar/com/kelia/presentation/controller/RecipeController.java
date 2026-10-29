package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.model.mongo.Review;
import ar.com.kelia.domain.service.AuthService;
import ar.com.kelia.domain.service.UserService;
import ar.com.kelia.domain.usecase.mongo.*;
import ar.com.kelia.infrastructure.dto.mongo.RecipeCreatedDTO;
import ar.com.kelia.infrastructure.dto.mongo.RecipeDTO;
import ar.com.kelia.presentation.mapper.RecipeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private CreateRecipeUseCase createRecipeUseCase;
    @Autowired
    private DeleteRecipeUseCase deleteRecipeUseCase;
    @Autowired
    private GetRecipeByIDCaseUse getRecipeByIDCaseUse;
    @Autowired
    private GetRecipesUseCase getRecipesUseCase;
    @Autowired
    private UserService userService;
    @Autowired
    RecipeMapper recipeMapper;

    @Operation(summary = "Create a new recipe", description = "Creates a recipe in the database")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Recipe to create",
            required = true,
            content = @Content(schema = @Schema(implementation = RecipeCreatedDTO.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recipe successfully created"),
            @ApiResponse(responseCode = "204", description = "uid not valid user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> create(
            @Parameter(description = "UID of the user who creates the recipe", required = true)
            @RequestParam String uid,

            @org.springframework.web.bind.annotation.RequestBody RecipeCreatedDTO dto
    ) {
        try {
            return userService.findByUid(uid)
                    .map(user -> {
                        createRecipeUseCase.execute(RecipeMapper.toEntity(dto, user));
                        return ResponseEntity.status(HttpStatus.CREATED).body("Recipe created");
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).body("uid not valid user"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    @Operation(summary = "Search for recipes", description = "Returns recipes by optional filters: term, category, filter")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recipes retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getRecipes(
            @Parameter(description = "Search term") @RequestParam(required = false) String term,
            @Parameter(description = "Recipe category") @RequestParam(required = false) String category,
            @Parameter(description = "Filter criteria") @RequestParam(name = "filter", required = false) String filter,
            @Parameter(description = "Max results") @RequestParam(name = "q", defaultValue = "10") int limit) {

        List<Recipe> recipes = getRecipesUseCase.execute(term, category, filter, limit);

        for (Recipe recipe : recipes) {
            double averageRating = 0.0;
            if (recipe.getReviews() != null && !recipe.getReviews().isEmpty()) {
                averageRating = recipe.getReviews().stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);
            }
            recipe.setRating(averageRating);
        }

        return ResponseEntity.ok(recipes);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get recipe by ID", description = "Retrieves a single recipe based on its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recipe found"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getRecipes(
            @Parameter(description = "ID of the recipe to retrieve") @PathVariable String id) {
        try {
            Recipe recipe = getRecipeByIDCaseUse.execute(id);

            double averageRating = 0.0;
            if (recipe.getReviews() != null && !recipe.getReviews().isEmpty()) {
                averageRating = recipe.getReviews().stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);
            }
            recipe.setRating(averageRating);

            return ResponseEntity.ok(recipe);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete recipe by ID", description = "Deletes a recipe from the database by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Recipe successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(
            @Parameter(description = "ID of the recipe to delete") @PathVariable String id) {
        try {
            deleteRecipeUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Recipe deleted");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
