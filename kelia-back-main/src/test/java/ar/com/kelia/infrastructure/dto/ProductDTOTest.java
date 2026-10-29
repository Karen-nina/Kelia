package ar.com.kelia.infrastructure.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para el DTO de productos.
 * Prueba la funcionalidad de constructores, getters y setters del DTO de
 * productos.
 */
class ProductDTOTest {

    /**
     * Prueba los constructores, getters y setters del ProductDTO.
     * Verifica que se puedan crear objetos con el constructor por defecto y con
     * parámetros,
     * y que los getters y setters funcionen correctamente.
     */
    @Test
    void testConstructorsAndGettersSetters() {
        // Arrange - Crear DTO usando constructor por defecto y setters
        ProductDTO dto = new ProductDTO();
        dto.setTitle("title");
        dto.setThumbnail("thumb");
        dto.setLink("link");
        dto.setSeller("seller");
        dto.setCertificate(true);

        // Assert - Verificar que los getters devuelvan los valores correctos
        assertEquals("title", dto.getTitle());
        assertEquals("thumb", dto.getThumbnail());
        assertEquals("link", dto.getLink());
        assertEquals("seller", dto.getSeller());
        assertTrue(dto.isCertificate());

        // Arrange - Crear DTO usando constructor con parámetros
        ProductDTO dto2 = new ProductDTO("title2", "thumb2", "link2", "seller2", false);

        // Assert - Verificar que los getters devuelvan los valores correctos
        assertEquals("title2", dto2.getTitle());
        assertEquals("thumb2", dto2.getThumbnail());
        assertEquals("link2", dto2.getLink());
        assertEquals("seller2", dto2.getSeller());
        assertFalse(dto2.isCertificate());
    }
}