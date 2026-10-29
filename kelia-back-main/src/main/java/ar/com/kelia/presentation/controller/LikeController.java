package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.mongo.CommentPostUseCase;
import ar.com.kelia.domain.usecase.mongo.LikePostUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {
    @Autowired
    LikePostUseCase useCase;

    @PostMapping()
    @Operation(summary = "add like post", description = "like a specific post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "like add successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    public ResponseEntity<?> comment(
            @Parameter(description = "ID of the post to like on", required = true)
            @RequestParam String postId,
            @Parameter(description = "ID of the user creating the comment", required = true)
            @RequestParam String uid) {
        try {
            this.useCase.execute(postId, uid);
            return ResponseEntity.status(HttpStatus.CREATED).body("like add-> " + postId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
