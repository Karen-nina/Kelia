package ar.com.kelia.infrastructure.provider;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.infrastructure.dto.TokenMLDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenMLServiceServiceImplTest {
    private TokenMLApiClient apiClient;
    private TokenMLServiceServiceImpl service;

    @BeforeEach
    void setUp() {
        apiClient = mock(TokenMLApiClient.class);
        service = new TokenMLServiceServiceImpl(apiClient);
    }

    @Test
    void testGetTokenExitoso() {
        // Caso exitoso: el DTO tiene todos los campos
        TokenMLDTO dto = new TokenMLDTO();
        dto.setAccessToken("token");
        dto.setTokenType("bearer");
        dto.setExpiresIn(3600);
        dto.setRefreshToken("refresh");
        when(apiClient.getToken(anyString(), anyString())).thenReturn(dto);
        TokenML token = service.getToken("authorization_code", "code");
        assertEquals("token", token.getAccessToken());
        assertEquals("bearer", token.getTokenType());
        assertEquals(3600, token.getExpiresIn());
        assertEquals("refresh", token.getRefreshToken());
    }

    @Test
    void testGetTokenDtoNull() {
        // Error: el DTO es null
        when(apiClient.getToken(anyString(), anyString())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.getToken("authorization_code", "code"));
    }

    @Test
    void testGetTokenCamposNulos() {
        // Error: el DTO tiene campos nulos
        TokenMLDTO dto = new TokenMLDTO();
        // No se setean campos
        when(apiClient.getToken(anyString(), anyString())).thenReturn(dto);
        TokenML token = service.getToken("authorization_code", "code");
        assertNull(token.getAccessToken());
        assertNull(token.getTokenType());
        assertEquals(0, token.getExpiresIn());
        assertNull(token.getRefreshToken());
    }
}