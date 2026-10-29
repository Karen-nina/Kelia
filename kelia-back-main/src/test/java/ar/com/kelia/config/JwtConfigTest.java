package ar.com.kelia.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test para JwtConfig: cubre el método decodeToken usando un JWT simple
class JwtConfigTest {

    @Test
    void testDecodeToken() {
        // Comentario: Este test verifica que el método decodeToken puede decodificar un
        // JWT válido.
        // No se valida la firma, solo se parsea el token.
        // El JWT usado aquí es solo de ejemplo y no requiere clave real.
        String header = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"; // {"alg":"HS256","typ":"JWT"}
        String payload = "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikp1YW4gUGVyZXoiLCJpYXQiOjE1MTYyMzkwMjJ9"; // {"sub":"1234567890","name":"Juan
                                                                                                         // Perez","iat":1516239022}
        String signature = "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"; // dummy
        String token = header + "." + payload + "." + signature;

        DecodedJWT decoded = JwtConfig.decodeToken(token);

        // Verificar que el token se decodifica correctamente
        assertNotNull(decoded);
        assertEquals("1234567890", decoded.getSubject());
        assertEquals("Juan Perez", decoded.getClaim("name").asString());
        assertEquals(1516239022L, decoded.getIssuedAt().getTime() / 1000); // iat en segundos
    }

    @Test
    void testDecodeTokenWithInvalidToken() {
        // Comentario: Este test verifica que un token mal formado lanza excepción
        String invalidToken = "token_invalido";
        assertThrows(Exception.class, () -> JwtConfig.decodeToken(invalidToken));
    }
}