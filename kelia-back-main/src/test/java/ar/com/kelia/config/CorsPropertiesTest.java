package ar.com.kelia.config;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class CorsPropertiesTest {
    @Test
    void testGettersAndSetters() {
        CorsProperties props = new CorsProperties();
        props.setAllowedOrigins(Collections.singletonList("*"));
        props.setAllowedMethods(Collections.singletonList("GET"));
        props.setAllowedHeaders(Collections.singletonList("*"));
        props.setAllowCredentials(true);
        assertEquals(Collections.singletonList("*"), props.getAllowedOrigins());
        assertEquals(Collections.singletonList("GET"), props.getAllowedMethods());
        assertEquals(Collections.singletonList("*"), props.getAllowedHeaders());
        assertTrue(props.isAllowCredentials());
    }
}