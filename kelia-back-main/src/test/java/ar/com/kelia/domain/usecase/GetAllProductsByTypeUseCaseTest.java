package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.service.ProductANMATService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Test básico: verifica que el método execute llama al servicio y retorna la lista
class GetAllProductsByTypeUseCaseTest {
    @Test
    void testExecute() {
        ProductANMATService service = Mockito.mock(ProductANMATService.class);
        Mockito.when(service.findAllByType("type")).thenReturn(Collections.emptyList());

        GetAllProductsByTypeUseCase useCase = new GetAllProductsByTypeUseCase();
        // El campo service es private, usar reflexión para setearlo
        try {
            java.lang.reflect.Field f = GetAllProductsByTypeUseCase.class.getDeclaredField("service");
            f.setAccessible(true);
            f.set(useCase, service);
        } catch (Exception e) {
            fail(e);
        }

        List<ProductANMAT> result = useCase.execute("type");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}