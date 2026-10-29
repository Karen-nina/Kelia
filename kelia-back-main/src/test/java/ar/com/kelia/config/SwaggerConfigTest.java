package ar.com.kelia.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {
    @Test
    void testCustomOpenAPI() {
        SwaggerConfig config = new SwaggerConfig();
        assertNotNull(config.customOpenAPI());
    }
}