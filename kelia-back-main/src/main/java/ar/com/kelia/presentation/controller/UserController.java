package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.FirebaseUserDetails;
import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.service.UserService;
import ar.com.kelia.domain.usecase.CreateUserUseCase;
import ar.com.kelia.infrastructure.dto.UserDTO;
import ar.com.kelia.infrastructure.dto.UserMeDTO;
import ar.com.kelia.presentation.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private CreateUserUseCase useCase;
    @Autowired
    private UserService service;

    @PostMapping()
    @Operation(summary = "Create a new user", description = "Creates a user in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            )
            @org.springframework.web.bind.annotation.RequestBody UserDTO dto) {
        try {
            if (null == dto) {
                return ResponseEntity.badRequest().body("user not found.");
            }
            useCase.execute(UserMapper.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Get user by token", description = "Get full user data by token bearer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        FirebaseUserDetails userDetails = (FirebaseUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUid();

        Optional<User> userOpt = service.findByUid(uid);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();

        UserMeDTO dto  = new UserMeDTO(user.getDisplayName(), user.getUsername(), user.getAvatar(), user.getBirthDate(), user.getZipCode(), user.isActive());

        return ResponseEntity.ok(dto);
    }


}
