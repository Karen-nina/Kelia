package ar.com.kelia.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SchedulerConfigTest {
    @Test
    void testInstantiation() {
        SchedulerConfig config = new SchedulerConfig();
        assertNotNull(config);
    }
}