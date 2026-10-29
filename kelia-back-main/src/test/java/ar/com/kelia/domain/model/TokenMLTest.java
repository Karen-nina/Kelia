package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TokenMLTest {

    @Test
    void shouldCreateTokenUsingCustomConstructor() {
        String accessToken = "access";
        String tokenType = "Bearer";
        int expiresIn = 3600;
        String refreshToken = "refresh";

        TokenML token = new TokenML(accessToken, tokenType, expiresIn, refreshToken);

        assertEquals("access", token.getAccessToken());
        assertEquals("Bearer", token.getTokenType());
        assertEquals(3600, token.getExpiresIn());
        assertEquals("refresh", token.getRefreshToken());
        assertNull(token.getCreatedAt()); // todavía no se ejecutó prePersist
        assertNull(token.getExpiresAt());
    }

    @Test
    void shouldSetCreatedAtAndExpiresAtOnPrePersist() {
        TokenML token = new TokenML("abc", "Bearer", 120, "xyz");
        token.prePersist();

        LocalDateTime now = LocalDateTime.now();
        assertNotNull(token.getCreatedAt());
        assertNotNull(token.getExpiresAt());
        assertTrue(token.getExpiresAt().isAfter(token.getCreatedAt()));
        assertTrue(token.getExpiresAt().isBefore(now.plusSeconds(125))); // margen de 5 seg
    }

    @Test
    void shouldCreateEmptyTokenWithDefaultConstructor() {
        TokenML token = new TokenML();

        assertNull(token.getAccessToken());
        assertEquals(0, token.getExpiresIn());
    }
}
