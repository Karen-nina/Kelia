package ar.com.kelia.domain.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para el servicio de autenticación con Firebase.
 * Prueba la funcionalidad de verificación de tokens de identificación de
 * Firebase.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private FirebaseAuth firebaseAuth;

    @Mock
    private FirebaseToken firebaseToken;

    @InjectMocks
    private AuthService authService;

    /**
     * Configuración inicial para cada prueba.
     * Configura el mock estático de FirebaseAuth para las pruebas.
     */
    @BeforeEach
    void setUp() {
        // Mock static FirebaseAuth.getInstance()
        try (MockedStatic<FirebaseAuth> mockedFirebaseAuth = mockStatic(FirebaseAuth.class)) {
            mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(firebaseAuth);
        }
    }

    /**
     * Prueba que el método verifyIdToken devuelva un FirebaseToken cuando el token
     * es válido.
     * Verifica que se procese correctamente un token válido de Firebase.
     */
    @Test
    void verifyIdToken_ShouldReturnFirebaseToken_WhenValidToken() throws Exception {
        // Arrange - Preparar datos de prueba
        String validToken = "valid-token";
        try (MockedStatic<FirebaseAuth> mockedFirebaseAuth = mockStatic(FirebaseAuth.class)) {
            mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(firebaseAuth);
            when(firebaseAuth.verifyIdToken(validToken)).thenReturn(firebaseToken);

            // Act - Ejecutar el método a probar
            FirebaseToken result = authService.verifyIdToken(validToken);

            // Assert - Verificar resultados esperados
            assertNotNull(result);
            assertEquals(firebaseToken, result);
            verify(firebaseAuth, times(1)).verifyIdToken(validToken);
        }
    }

    /**
     * Prueba que el método verifyIdToken lance una excepción cuando el token es
     * inválido.
     * Verifica que se maneje correctamente el error de token inválido.
     */
    @Test
    void verifyIdToken_ShouldThrowRuntimeException_WhenInvalidToken() throws Exception {
        // Arrange - Preparar datos de prueba
        String invalidToken = "invalid-token";
        try (MockedStatic<FirebaseAuth> mockedFirebaseAuth = mockStatic(FirebaseAuth.class)) {
            mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(firebaseAuth);
            when(firebaseAuth.verifyIdToken(invalidToken)).thenThrow(new RuntimeException("Invalid token"));

            // Act & Assert - Ejecutar y verificar excepción
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> authService.verifyIdToken(invalidToken));

            assertEquals("Token invalid and expired.", exception.getMessage());
            verify(firebaseAuth, times(1)).verifyIdToken(invalidToken);
        }
    }

    /**
     * Prueba que el método verifyIdToken lance una excepción cuando el token está
     * expirado.
     * Verifica que se maneje correctamente el error de token expirado.
     */
    @Test
    void verifyIdToken_ShouldThrowRuntimeException_WhenTokenExpired() throws Exception {
        // Arrange - Preparar datos de prueba
        String expiredToken = "expired-token";
        try (MockedStatic<FirebaseAuth> mockedFirebaseAuth = mockStatic(FirebaseAuth.class)) {
            mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(firebaseAuth);
            when(firebaseAuth.verifyIdToken(expiredToken)).thenThrow(new RuntimeException("Token expired"));

            // Act & Assert - Ejecutar y verificar excepción
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> authService.verifyIdToken(expiredToken));

            assertEquals("Token invalid and expired.", exception.getMessage());
            verify(firebaseAuth, times(1)).verifyIdToken(expiredToken);
        }
    }

    /**
     * Prueba que el método verifyIdToken lance una excepción cuando el token es
     * null.
     * Verifica que se maneje correctamente el error de token nulo.
     */
    @Test
    void verifyIdToken_ShouldThrowRuntimeException_WhenNullToken() throws Exception {
        // Arrange - Preparar datos de prueba
        try (MockedStatic<FirebaseAuth> mockedFirebaseAuth = mockStatic(FirebaseAuth.class)) {
            mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(firebaseAuth);
            when(firebaseAuth.verifyIdToken(null)).thenThrow(new RuntimeException("Null token"));

            // Act & Assert - Ejecutar y verificar excepción
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> authService.verifyIdToken(null));

            assertEquals("Token invalid and expired.", exception.getMessage());
            verify(firebaseAuth, times(1)).verifyIdToken(null);
        }
    }
}
