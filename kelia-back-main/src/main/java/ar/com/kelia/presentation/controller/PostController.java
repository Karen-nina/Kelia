package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.UserService;
import ar.com.kelia.domain.usecase.mongo.CreatePostUseCase;
import ar.com.kelia.domain.usecase.mongo.GetPostUseCase;
import ar.com.kelia.domain.usecase.mongo.GetPostRandomUseCase;
import ar.com.kelia.domain.usecase.mongo.GetPostsFromFilterUseCase;
import ar.com.kelia.infrastructure.dto.mongo.PostCreatedDTO;
import ar.com.kelia.presentation.mapper.PostMapper;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    CreatePostUseCase createPostUseCase;

    @Autowired
    GetPostsFromFilterUseCase getPostsFromFilterUseCase;

    @Autowired
    GetPostUseCase getPostUseCase;

    @Autowired
    GetPostRandomUseCase getPostRandomUseCase;

    @Autowired
    PostMapper postMapper;

    @Autowired
    private UserService userService;

    @PostMapping()
    @Operation(summary = "Create a new post", description = "Creates a post from the provided PostCreatedDTO")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Post data to create",
            required = true,
            content = @Content(schema = @Schema(implementation = PostCreatedDTO.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid post data")
    })
    public ResponseEntity<String> createdPost(
            @Parameter(description = "UID of the user creating the post", required = true)
            @RequestParam String uid,

            @org.springframework.web.bind.annotation.RequestBody PostCreatedDTO dto
    ) {
        try {
            return userService.findByUid(uid)
                    .map(user -> {
                        Post post = postMapper.convertDtoToPost(dto, user);
                        post.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));
                        createPostUseCase.execute(post);
                        return ResponseEntity.status(HttpStatus.CREATED).body("post created");
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).body("uid not valid user"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    @Operation(summary = "Get posts by term", description = "Retrieve posts filtered by a specific term")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No posts found with the specified filter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getPostsForParam(
            @Parameter(description = "Filter term to search posts")
            @RequestParam(value = "term", required = false) String term) {
        try {
            List<Post> posts;
            if (term == null || term.isEmpty()){
                posts = getPostUseCase.execute();
                if (posts.isEmpty()) {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "No posts found with the specified filter.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            }else{
                 posts = getPostsFromFilterUseCase.execute(term);
                if (posts.isEmpty()) {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "No posts found with the specified filter.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            }
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
