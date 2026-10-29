package ar.com.kelia.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FirebaseTokenFilterTest {
    @Test
    void testInstantiation() {
        FirebaseTokenFilter filter = new FirebaseTokenFilter();
        assertNotNull(filter);
    }
}