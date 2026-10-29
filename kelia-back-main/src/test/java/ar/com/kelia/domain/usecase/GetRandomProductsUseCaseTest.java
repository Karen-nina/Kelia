package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.service.SearchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Test básico: verifica que el método execute llama al servicio y retorna la lista
class GetRandomProductsUseCaseTest {
    @Test
    void testExecute() {
        SearchService service = Mockito.mock(SearchService.class);
        Mockito.when(service.searchRandomProduct()).thenReturn(Collections.emptyList());

        GetRandomProductsUseCase useCase = new GetRandomProductsUseCase();
        // El campo service es private, usar reflexión para setearlo
        try {
            java.lang.reflect.Field f = GetRandomProductsUseCase.class.getDeclaredField("service");
            f.setAccessible(true);
            f.set(useCase, service);
        } catch (Exception e) {
            fail(e);
        }

        List<Product> result = useCase.execute();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}