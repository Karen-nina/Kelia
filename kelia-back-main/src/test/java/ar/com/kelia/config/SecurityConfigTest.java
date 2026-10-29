package ar.com.kelia.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Test para SecurityConfig: cubre el constructor, corsConfigurationSource y passwordEncoder
class SecurityConfigTest {

    @Test
    void testConstructorAndCorsConfigurationSource() {
        // Comentario: Este test verifica que el bean de CORS se configura correctamente
        CorsProperties corsProperties = mock(CorsProperties.class);
        when(corsProperties.getAllowedOrigins()).thenReturn(Arrays.asList("http://localhost:3000"));
        when(corsProperties.getAllowedMethods()).thenReturn(Arrays.asList("GET", "POST"));
        when(corsProperties.getAllowedHeaders()).thenReturn(Arrays.asList("Authorization", "Content-Type"));
        when(corsProperties.isAllowCredentials()).thenReturn(true);

        SecurityConfig config = new SecurityConfig(corsProperties);
        CorsConfigurationSource source = config.corsConfigurationSource();
        assertNotNull(source);
        assertTrue(source instanceof UrlBasedCorsConfigurationSource);
    }

    @Test
    void testPasswordEncoder() {
        // Comentario: Este test verifica que el bean PasswordEncoder funciona
        // correctamente
        SecurityConfig config = new SecurityConfig(mock(CorsProperties.class));
        PasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder);
        String rawPassword = "claveSegura123";
        String encoded = encoder.encode(rawPassword);
        assertNotEquals(rawPassword, encoded);
        assertTrue(encoder.matches(rawPassword, encoded));
    }
}