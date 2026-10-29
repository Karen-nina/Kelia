package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.service.ProductANMATService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

// Test básico: solo verifica que el método execute se puede invocar sin errores
class GetAllProductsByTermUseCaseTest {
    @Test
    void testExecute() {
        // Mock del servicio
        ProductANMATService service = Mockito.mock(ProductANMATService.class);
        Mockito.when(service.findAllByTerm(Mockito.anyString())).thenReturn(Collections.emptyList());

        // Instanciar el use case y setear el mock usando reflexión
        GetAllProductsByTermUseCase useCase = new GetAllProductsByTermUseCase();
        try {
            java.lang.reflect.Field f = GetAllProductsByTermUseCase.class.getDeclaredField("service");
            f.setAccessible(true);
            f.set(useCase, service);
        } catch (Exception e) {
            fail(e);
        }

        // Ejecutar y verificar que no es null
        Map<String, java.util.List<Object>> result = useCase.execute("test");
        assertNotNull(result);
        assertTrue(result.containsKey("filter-type"));
        assertTrue(result.containsKey("filter-brand"));
        assertTrue(result.containsKey("products"));
    }
}