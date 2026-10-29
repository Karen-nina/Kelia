package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.SaveTokenMLUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/token-ml")
public class TokenMLController {

    @Autowired
    private SaveTokenMLUseCase useCase;

    @PostMapping("")
    @Operation(summary = "Save Mercado Libre OAuth2 token", description = "Saves the OAuth2 token received from Mercado Libre using grant and code parameters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    })
    public ResponseEntity<Void> saveToken(
            @Parameter(description = "Grant type", required = true)
            @RequestParam("grant") String grant,
            @Parameter(description = "Authorization code", required = true)
            @RequestParam("code") String code) {
        useCase.execute(grant, code);
        return ResponseEntity.ok().build();
    }
}
