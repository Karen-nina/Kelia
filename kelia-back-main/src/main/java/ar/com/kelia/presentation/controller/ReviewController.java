package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.mongo.ReviewRecipeCreatedCaseUse;
import ar.com.kelia.infrastructure.dto.ReviewDTO;
import ar.com.kelia.infrastructure.dto.mongo.PostCreatedDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    ReviewRecipeCreatedCaseUse useCase;

    @PostMapping()
    @Operation(summary = "Create a new Review", description = "Creates a Review from the provided ReviewDTO")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Review data to create",
            required = true,
            content = @Content(schema = @Schema(implementation = ReviewDTO.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Review data")
    })
    public ResponseEntity<?> review(
            @Parameter(description = "UID of the user creating the post", required = true)

            @org.springframework.web.bind.annotation.RequestBody ReviewDTO dto
    ) {
        try {
            if (dto.getRating() < 0 || dto.getRating() > 5) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("\n" + "The rating must be between 0 and 5");
            }
            this.useCase.execute(dto.getRecipeId(),dto.getPlaceId(), dto.getUid(), dto.getContent(), dto.getRating());
            return ResponseEntity.status(HttpStatus.CREATED).body("review created");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
