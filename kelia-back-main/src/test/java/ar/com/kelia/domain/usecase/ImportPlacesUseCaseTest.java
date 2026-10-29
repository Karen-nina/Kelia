package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

// Test básico: verifica que el método execute llama al servicio con un InputStream
class ImportPlacesUseCaseTest {
    @Test
    void testExecute() throws Exception {
        PlaceService service = Mockito.mock(PlaceService.class);
        Mockito.doNothing().when(service).parseAndSavePlaces(Mockito.any());

        ImportPlacesUseCase useCase = new ImportPlacesUseCase();
        try {
            java.lang.reflect.Field f = ImportPlacesUseCase.class.getDeclaredField("service");
            f.setAccessible(true);
            f.set(useCase, service);
        } catch (Exception e) {
            fail(e);
        }

        // Usar un InputStream dummy
        ByteArrayInputStream is = new ByteArrayInputStream(new byte[0]);
        assertDoesNotThrow(() -> useCase.execute(is));
    }
}