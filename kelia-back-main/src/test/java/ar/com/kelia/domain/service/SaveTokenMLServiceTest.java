package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.domain.repository.TokenMLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaveTokenMLServiceTest {

    @Mock
    TokenMLServiceService tokenClient;

    @Mock
    TokenMLRepository repository;

    @InjectMocks
    SaveTokenMLService saveTokenMLService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAdminTokenSuccessfully() {
        TokenML token = new TokenML();
        when(tokenClient.getToken("authorization_code", "CODE123")).thenReturn(token);

        saveTokenMLService.saveTokenMLAdmin("authorization_code", "CODE123");

        verify(tokenClient).getToken("authorization_code", "CODE123");
        verify(repository).save(token);
    }

    @Test
    void shouldSaveRegularToken() {
        TokenML token = new TokenML();
        saveTokenMLService.saveTokenML(token);

        verify(repository).save(token);
    }

    @Test
    void shouldReturnValidTokenIfNotExpired() {
        TokenML token = new TokenML();
        token.setExpiresAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")).plusMinutes(30));

        when(repository.findLatestValidToken()).thenReturn(Optional.of(token));

        TokenML result = saveTokenMLService.getValidToken();

        assertEquals(token, result);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldRefreshTokenIfExpired() {
        TokenML expiredToken = new TokenML();
        expiredToken.setRefreshToken("refresh123");
        expiredToken.setExpiresAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")).minusMinutes(5));

        TokenML refreshed = new TokenML();
        when(repository.findLatestValidToken()).thenReturn(Optional.of(expiredToken));
        when(tokenClient.getToken("refresh_token", "refresh123")).thenReturn(refreshed);

        TokenML result = saveTokenMLService.getValidToken();

        assertEquals(refreshed, result);
        verify(repository).save(refreshed);
    }

    @Test
    void shouldThrowExceptionWhenNoTokenFound() {
        when(repository.findLatestValidToken()).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> saveTokenMLService.getValidToken());

        assertEquals("No token was found.", ex.getMessage());
    }

    @Test
    void shouldRefreshTokenInScheduledTaskIfExpired() {
        TokenML expiredToken = new TokenML();
        expiredToken.setExpiresAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")).minusMinutes(1));
        expiredToken.setRefreshToken("refreshXYZ");

        TokenML newToken = new TokenML();

        when(repository.findLatestValidToken()).thenReturn(Optional.of(expiredToken));
        when(tokenClient.getToken("refresh_token", "refreshXYZ")).thenReturn(newToken);

        saveTokenMLService.checkAndRefreshToken();

        verify(repository).save(newToken);
    }

    @Test
    void shouldNotRefreshIfTokenStillValidInScheduledTask() {
        TokenML validToken = new TokenML();
        validToken.setExpiresAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")).plusMinutes(10));

        when(repository.findLatestValidToken()).thenReturn(Optional.of(validToken));

        saveTokenMLService.checkAndRefreshToken();

        verify(repository, never()).save(any());
    }

    @Test
    void shouldLogIfNoTokenExistsInScheduledTask() {
        when(repository.findLatestValidToken()).thenReturn(Optional.empty());

        saveTokenMLService.checkAndRefreshToken();

        verify(tokenClient, never()).getToken(any(), any());
    }
}
