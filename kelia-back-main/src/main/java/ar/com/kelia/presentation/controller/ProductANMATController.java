package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.usecase.GetAllProductsByTermUseCase;
import ar.com.kelia.domain.usecase.GetAllProductsByTypeUseCase;
import ar.com.kelia.domain.usecase.ImportProductFromExcelUseCase;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productANMAT")
public class ProductANMATController {

    @Autowired
    private ImportProductFromExcelUseCase importProductFromExcelUseCase;

    @Autowired
    private GetAllProductsByTermUseCase getAllProductsByTermUseCase;

    @Autowired
    private GetAllProductsByTypeUseCase getAllProductsByTypeUseCase;

    @PostMapping()
    @Operation(summary = "Import product list from Excel file", description = "Reads and imports products from a provided Excel (.xlsx) file")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File processed successfully"),
            @ApiResponse(responseCode = "400", description = "Error processing file", content = @Content)
    })
    public ResponseEntity<String> importExcel(
            @Parameter(description = "Excel file to import", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            importProductFromExcelUseCase.execute(file);
            return ResponseEntity.status(HttpStatus.OK).body("File processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing file");
        }
    }

    @GetMapping()
    @Operation(summary = "Get products by search term or type", description = "Returns products whose name or description matches the given term or type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<?> getProducts(
            @Parameter(description = "Search term used to filter products")
            @RequestParam(value = "term", required = false) String term,
            @Parameter(description = "Search type used to filter products")
            @RequestParam(value = "type", required = false) String type) {
        try {
            if (type == null && term != null){
                Map<String, List<Object>> products = getAllProductsByTermUseCase.execute(term);
                return ResponseEntity.ok(products);
            } else if (type != null && term == null){
                List<ProductANMAT> products = getAllProductsByTypeUseCase.execute(type);
                return ResponseEntity.ok(products);
            } else if (type != null && term != null) {
                return ResponseEntity.badRequest().body("No se pueden enviar ambos parámetros a la vez.");
            } else {
                return ResponseEntity.badRequest().body("Debe enviar al menos uno de los parámetros: term o type.");
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while retrieving products.");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}