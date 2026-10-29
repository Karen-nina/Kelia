package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.service.ProductANMATFromExcelService;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;

class ImportProductFromExcelUseCaseTest {

    @Test
    void shouldCallImportServiceWithMultipartFile() {
        // Mock del servicio y archivo
        ProductANMATFromExcelService mockService = mock(ProductANMATFromExcelService.class);
        MultipartFile mockFile = mock(MultipartFile.class);

        // Instanciar el UseCase con el mock del servicio
        ImportProductFromExcelUseCase useCase = new ImportProductFromExcelUseCase(mockService); // Usar setter si no tenés constructor

        // Ejecutar método
        useCase.execute(mockFile);

        // Verificar que se llamó correctamente
        verify(mockService).importProductFromExcel(mockFile);
    }
}
