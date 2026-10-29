package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Test básico: verifica que el método execute llama al servicio y retorna la lista
class GetPlacesFromTermUseCaseTest {
    @Test
    void testExecute() {
        PlaceService service = Mockito.mock(PlaceService.class);
        Mockito.when(service.findByNameContainingIgnoreCase("test")).thenReturn(Collections.emptyList());

        GetPlacesFromTermUseCase useCase = new GetPlacesFromTermUseCase();
        try {
            java.lang.reflect.Field f = GetPlacesFromTermUseCase.class.getDeclaredField("service");
            f.setAccessible(true);
            f.set(useCase, service);
        } catch (Exception e) {
            fail(e);
        }

        List<Place> result = useCase.execute("test");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}