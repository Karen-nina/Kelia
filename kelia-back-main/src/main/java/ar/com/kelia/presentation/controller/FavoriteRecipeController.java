package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.FavoriteRecipeUseCase;
import ar.com.kelia.domain.usecase.GetFavoriteRecipeByUidUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteRecipeController {

    @Autowired
    FavoriteRecipeUseCase favoriteRecipeUseCase;

    @Autowired
    GetFavoriteRecipeByUidUseCase getFavoriteRecipeByUidUseCase;

    @PostMapping()
    @Operation(summary = "Add to favorite recipes", description = "Add a recipe to favorites per user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Favorite add successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    public ResponseEntity<?> favorite(
            @Parameter(description = "ID of the post to favorite on", required = true)
            @RequestParam String recipeId,
            @Parameter(description = "ID of the user", required = true)
            @RequestParam String uid) {
        try {
            this.favoriteRecipeUseCase.execute(recipeId, uid);
            return ResponseEntity.status(HttpStatus.CREATED).body("recipe favorite-> " + recipeId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    @Operation(summary = "Get to favorite recipes", description = "Get a recipe to favorites per user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Get favorite successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    public ResponseEntity<?> getFavorites(
            @Parameter(description = "ID of the user", required = true)
            @RequestParam String uid) {
        try {
            return ResponseEntity.ok(this.getFavoriteRecipeByUidUseCase.execute(uid));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
