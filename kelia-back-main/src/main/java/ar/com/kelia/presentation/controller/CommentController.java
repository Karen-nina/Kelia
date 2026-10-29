package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.mongo.CommentPostUseCase;
import ar.com.kelia.infrastructure.dto.mongo.CommentDTO;
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
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentPostUseCase useCase;

    @PostMapping()
    @Operation(summary = "Create a new comment in post", description = "Creates a comment from the provided commentDTO")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Comment data to create",
            required = true,
            content = @Content(schema = @Schema(implementation = CommentDTO.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid comment data")
    })
    public ResponseEntity<?> comment(
            @org.springframework.web.bind.annotation.RequestBody CommentDTO dto) {
        try {
            this.useCase.execute(dto.getPost_id(), dto.getUid(), dto.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).body("post comment-> " + dto.getPost_id());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
