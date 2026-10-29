package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

// Tests para CreateUserUseCase cubriendo todos los flujos posibles
class CreateUserUseCaseTest {
    private UserService service;
    private CreateUserUseCase useCase;
    private User user;

    @BeforeEach
    void setUp() {
        // Configuración inicial para todos los tests
        service = Mockito.mock(UserService.class);
        useCase = new CreateUserUseCase();
        user = new User();
        user.setUid("test-uid");
        user.setDisplayName("Usuario Test");

        // Inyectar el mock del servicio usando reflection
        try {
            java.lang.reflect.Field f = CreateUserUseCase.class.getDeclaredField("service");
            f.setAccessible(true);
            f.set(useCase, service);
        } catch (Exception e) {
            fail("Error al configurar el test: " + e.getMessage());
        }
    }

    @Test
    void testExecuteUsuarioNuevo() {
        // Caso exitoso: crear usuario cuando el UID no existe
        Mockito.when(service.findByUid("test-uid")).thenReturn(Optional.empty());
        Mockito.doNothing().when(service).save(user);

        // No debe lanzar excepción
        assertDoesNotThrow(() -> useCase.execute(user));

        // Verificar que se llamó al método save
        Mockito.verify(service).save(user);
    }

    @Test
    void testExecuteUsuarioYaExiste() {
        // Caso de error: intentar crear usuario con UID que ya existe
        User existingUser = new User();
        existingUser.setUid("test-uid");
        Mockito.when(service.findByUid("test-uid")).thenReturn(Optional.of(existingUser));

        // Debe lanzar excepción
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(user));

        // Verificar el mensaje de error
        assertEquals("El uid de usuario ya está en uso.", exception.getMessage());

        // Verificar que NO se llamó al método save
        Mockito.verify(service, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testExecuteUsuarioConDatosCompletos() {
        // Caso: usuario con datos completos se crea correctamente
        user.setDisplayName("Usuario Test Completo");
        user.setAvatar("https://example.com/avatar.jpg");

        Mockito.when(service.findByUid("test-uid")).thenReturn(Optional.empty());
        Mockito.doNothing().when(service).save(user);

        assertDoesNotThrow(() -> useCase.execute(user));
        Mockito.verify(service).save(user);
    }

    @Test
    void testExecuteUsuarioConUidDiferente() {
        // Caso: verificar que funciona con diferentes UIDs
        user.setUid("otro-uid");
        Mockito.when(service.findByUid("otro-uid")).thenReturn(Optional.empty());
        Mockito.doNothing().when(service).save(user);

        assertDoesNotThrow(() -> useCase.execute(user));
        Mockito.verify(service).save(user);
    }
}