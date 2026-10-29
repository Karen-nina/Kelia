package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JpaTokenMLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para la implementación del repositorio de tokens de
 * MercadoLibre.
 * Prueba la funcionalidad de guardado y búsqueda de tokens en la base de datos.
 */
class TokenMLRepositoryImplTest {
    private JpaTokenMLRepository jpaRepository;
    private TokenMLRepositoryImpl repository;

    /**
     * Configuración inicial para cada prueba.
     * Crea los mocks necesarios para el repositorio JPA y la implementación del
     * repositorio.
     */
    @BeforeEach
    void setUp() {
        jpaRepository = mock(JpaTokenMLRepository.class);
        repository = new TokenMLRepositoryImpl(jpaRepository);
    }

    /**
     * Prueba que el método save mapee y delegue correctamente al repositorio JPA.
     * Verifica que se guarde el token con todos sus campos correctamente.
     */
    @Test
    void save_ShouldMapAndDelegateToJpa() {
        // Arrange - Preparar datos de prueba
        TokenML token = new TokenML();
        token.setAccessToken("access");
        token.setTokenType("Bearer");
        token.setExpiresIn(3600);
        token.setRefreshToken("refresh");

        // Act - Ejecutar el método a probar
        repository.save(token);

        // Assert - Verificar resultados esperados
        ArgumentCaptor<TokenML> captor = ArgumentCaptor.forClass(TokenML.class);
        verify(jpaRepository, times(1)).save(captor.capture());
        TokenML saved = captor.getValue();
        assertEquals("access", saved.getAccessToken());
        assertEquals("Bearer", saved.getTokenType());
        assertEquals(3600, saved.getExpiresIn());
        assertEquals("refresh", saved.getRefreshToken());
    }

    /**
     * Prueba que el método findLatestValidToken devuelva el token desde el
     * repositorio JPA.
     * Verifica que se encuentre y devuelva el token más reciente.
     */
    @Test
    void findLatestValidToken_ShouldReturnFromJpa() {
        // Arrange - Preparar datos de prueba
        TokenML token = new TokenML();
        when(jpaRepository.findTopByOrderByExpiresAtDesc()).thenReturn(Optional.of(token));

        // Act - Ejecutar el método a probar
        Optional<TokenML> result = repository.findLatestValidToken();

        // Assert - Verificar resultados esperados
        assertTrue(result.isPresent());
        assertEquals(token, result.get());
        verify(jpaRepository, times(1)).findTopByOrderByExpiresAtDesc();
    }

    /**
     * Prueba que el método findLatestValidToken devuelva Optional vacío cuando no
     * hay tokens.
     * Verifica que se maneje correctamente el caso cuando no existen tokens en la
     * base de datos.
     */
    @Test
    void findLatestValidToken_ShouldReturnEmptyIfNone() {
        // Arrange - Preparar datos de prueba
        when(jpaRepository.findTopByOrderByExpiresAtDesc()).thenReturn(Optional.empty());

        // Act - Ejecutar el método a probar
        Optional<TokenML> result = repository.findLatestValidToken();

        // Assert - Verificar resultados esperados
        assertFalse(result.isPresent());
        verify(jpaRepository, times(1)).findTopByOrderByExpiresAtDesc();
    }
}