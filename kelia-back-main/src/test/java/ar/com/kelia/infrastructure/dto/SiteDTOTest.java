package ar.com.kelia.infrastructure.dto;

import org.junit.jupiter.api.Test;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SiteDTOTest {
    @Test
    void testFields() {
        // Test básico: verificar que los campos se pueden inicializar correctamente
        SiteDTO dto = new SiteDTO();
        dto.sites = Collections.emptyList();
        dto.posts = Collections.emptyList();
        assertNotNull(dto.sites);
        assertNotNull(dto.posts);
    }
}