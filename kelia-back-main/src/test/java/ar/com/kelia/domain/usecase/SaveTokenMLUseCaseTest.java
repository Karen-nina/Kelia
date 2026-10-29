package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.service.SaveTokenMLService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para el caso de uso de guardado de tokens de MercadoLibre.
 * Prueba la funcionalidad de ejecución del caso de uso que delega al servicio.
 */
@ExtendWith(MockitoExtension.class)
class SaveTokenMLUseCaseTest {
    @Mock
    private SaveTokenMLService service;
    @InjectMocks
    private SaveTokenMLUseCase useCase;

    /**
     * Prueba que el método execute del caso de uso llame al servicio
     * correspondiente.
     * Verifica que se delegue correctamente la operación al servicio de guardado de
     * tokens.
     */
    @Test
    void execute_ShouldCallService() {
        // Arrange - Preparar datos de prueba
        String grant = "grant";
        String code = "code";

        // Act - Ejecutar el método a probar
        useCase.execute(grant, code);

        // Assert - Verificar resultados esperados
        verify(service, times(1)).saveTokenMLAdmin(grant, code);
    }
}