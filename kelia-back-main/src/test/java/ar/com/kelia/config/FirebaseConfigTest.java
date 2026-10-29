package ar.com.kelia.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FirebaseConfigTest {
    @Test
    void testInstantiation() {
        FirebaseConfig config = new FirebaseConfig();
        assertNotNull(config);
    }
}