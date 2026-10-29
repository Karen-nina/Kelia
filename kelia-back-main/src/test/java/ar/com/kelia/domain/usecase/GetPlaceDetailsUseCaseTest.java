package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.PlaceDetail;
import ar.com.kelia.domain.service.GooglePlacesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

// Test básico: verifica que el método execute llama al servicio y retorna el resultado
class GetPlaceDetailsUseCaseTest {
    @Test
    void testExecute() {
        GooglePlacesService service = Mockito.mock(GooglePlacesService.class);
        PlaceDetail detail = new PlaceDetail();
        Mockito.when(service.getPlaceDetails("id1")).thenReturn(detail);

        GetPlaceDetailsUseCase useCase = new GetPlaceDetailsUseCase(service);
        PlaceDetail result = useCase.execute("id1");
        assertSame(detail, result);
    }
}