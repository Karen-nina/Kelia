package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.ImportProductFromExcelUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para el controlador de productos ANMAT.
 * Prueba la funcionalidad de importación de productos desde archivos Excel.
 */
@ExtendWith(MockitoExtension.class)
class ProductANMATControllerTest {

    @Mock
    private ImportProductFromExcelUseCase useCase;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ProductANMATController controller;

    /**
     * Prueba que el método importExcel devuelva OK cuando el archivo se procesa
     * exitosamente.
     * Verifica que se llame al caso de uso y se devuelva la respuesta correcta.
     */
    @Test
    void importExcel_ShouldReturnOk_WhenFileProcessedSuccessfully() {
        // Arrange - Preparar datos de prueba
        doNothing().when(useCase).execute(multipartFile);

        // Act - Ejecutar el método a probar
        ResponseEntity<String> response = controller.importExcel(multipartFile);

        // Assert - Verificar resultados esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File processed successfully", response.getBody());
        verify(useCase, times(1)).execute(multipartFile);
    }

    /**
     * Prueba que el método importExcel devuelva BAD_REQUEST cuando se lanza una
     * excepción.
     * Verifica que se maneje correctamente el error y se devuelva la respuesta de
     * error apropiada.
     */
    @Test
    void importExcel_ShouldReturnBadRequest_WhenExceptionThrown() {
        // Arrange - Preparar datos de prueba
        doThrow(new RuntimeException("Error")).when(useCase).execute(multipartFile);

        // Act - Ejecutar el método a probar
        ResponseEntity<String> response = controller.importExcel(multipartFile);

        // Assert - Verificar resultados esperados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error processing file", response.getBody());
        verify(useCase, times(1)).execute(multipartFile);
    }
}
