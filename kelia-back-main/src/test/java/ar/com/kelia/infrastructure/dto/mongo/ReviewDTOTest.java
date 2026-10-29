package ar.com.kelia.infrastructure.dto.mongo;

import ar.com.kelia.infrastructure.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para el DTO de reviews de MongoDB.
 * Prueba la funcionalidad de constructores, getters y setters del DTO de reviews.
 */
class ReviewDTOTest {

    /**
     * Prueba los constructores, getters y setters del ReviewDTO.
     * Verifica que se puedan crear objetos con el constructor por defecto y con parámetros,
     * y que los getters y setters funcionen correctamente.
     */
    @Test
    void testConstructorsAndGettersSetters() {
        // Arrange - Preparar datos de prueba
        UserDTO user = new UserDTO();
        LocalDateTime now = LocalDateTime.now();

        // Arrange - Crear DTO usando constructor por defecto y setters
        ReviewDTO dto = new ReviewDTO();
        dto.setUser(user);
        dto.setRating(5);
        dto.setComment("comment");
        dto.setCreatedAt(now);

        // Assert - Verificar que los getters devuelvan los valores correctos
        assertEquals(user, dto.getUser());
        assertEquals(5, dto.getRating());
        assertEquals("comment", dto.getComment());
        assertEquals(now, dto.getCreatedAt());

        // Arrange - Crear DTO usando constructor con parámetros (4 params)
        ReviewDTO dto2 = new ReviewDTO(user, 4, "c2", now);

        // Assert - Verificar que los getters devuelvan los valores correctos
        assertEquals(user, dto2.getUser());
        assertEquals(4, dto2.getRating());
        assertEquals("c2", dto2.getComment());
        assertEquals(now, dto2.getCreatedAt());
    }
}
