package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.usecase.GetRandomProductsUseCase;
import ar.com.kelia.domain.usecase.SearchProductUseCase;
import ar.com.kelia.infrastructure.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private SearchProductUseCase searchProductUseCase;

    @Autowired
    private GetRandomProductsUseCase getRandomProductsUseCase;

    @GetMapping()
    @Operation(summary = "Search products by term", description = "Returns a list of products that match the search term or all products if term is not provided")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products returned successfully")
    })
    public ResponseEntity<List<Product>> searchProduct(
            @Parameter(description = "Search term to find matching products", required = false)
            @RequestParam(value = "term", required = false) String term) {

        if (term != null && !term.isEmpty()) {
            return ResponseEntity.ok(searchProductUseCase.execute(term));
        } else {
            return ResponseEntity.ok(getRandomProductsUseCase.execute());
        }
    }

}