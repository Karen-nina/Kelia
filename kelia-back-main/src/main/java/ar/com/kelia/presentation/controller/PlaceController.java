package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.model.PlaceDetail;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import ar.com.kelia.domain.usecase.GetPlaceDetailsUseCase;
import ar.com.kelia.domain.usecase.GetPlacesFromTermUseCase;
import ar.com.kelia.domain.usecase.ImportPlacesUseCase;
import ar.com.kelia.infrastructure.dto.mongo.PlaceDTO;
import ar.com.kelia.presentation.mapper.PlaceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    @Autowired
    private ImportPlacesUseCase importPlacesUseCase;

    @Autowired
    private GetPlacesFromTermUseCase getPlacesFromTermUseCase;

    @Autowired
    private GetPlaceDetailsUseCase getPlaceDetailsUseCase;

    @Autowired
    private PostService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Import places list from json file", description = "Reads and imports places from a provided json (.json) file")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File processed successfully"),
            @ApiResponse(responseCode = "400", description = "Error processing file", content = @Content)
    })
    public ResponseEntity<String> uploadJson(
            @Parameter(description = "JSON file to import", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            if (!file.getOriginalFilename().endsWith(".json")) {
                return ResponseEntity.badRequest().body("Invalid file type. Please upload a .json file.");
            }

            importPlacesUseCase.execute(file.getInputStream());
            return ResponseEntity.ok("File processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }


    @Operation(
            summary = "Search places and related posts by name",
            description = "Returns a list of places whose names contain the given keyword (case-insensitive), and also fetches related posts"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Places and related posts found"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPlacesAndPostsByName(@RequestParam(value = "name", required = false) String name) {
        try {
            List<Place> places = getPlacesFromTermUseCase.execute(name);
            List<PlaceDTO> placeDTOs = places.stream()
                    .map(PlaceMapper::toDto)
                    .toList();
            List<Post> posts = service.getPostFromFilter(name);
            Map<String, Object> response;
            if(posts == null){
                 response = new HashMap<>();
                response.put("places", placeDTOs);
            }else{
                 response = new HashMap<>();
                response.put("places", placeDTOs);
                response.put("posts", posts);
            }


            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching data: " + e.getMessage()));
        }
    }

    @GetMapping("/{placeId}")
    @Operation(summary = "Get place details by ID", description = "Retrieves detailed information about a place by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Place details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Place not found")
    })
    public PlaceDetail getPlaceDetails(
            @Parameter(description = "Unique identifier of the place", required = true)
            @PathVariable String placeId) {
        return getPlaceDetailsUseCase.execute(placeId);
    }

}

