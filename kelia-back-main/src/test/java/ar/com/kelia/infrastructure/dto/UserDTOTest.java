package ar.com.kelia.infrastructure.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para el DTO de usuarios.
 * Prueba la funcionalidad de constructores, getters y setters del DTO de usuarios.
 */
class UserDTOTest {

    /**
     * Prueba los constructores, getters y setters del UserDTO.
     * Verifica que se puedan crear objetos con el constructor por defecto y con
     * parámetros, y que los getters y setters funcionen correctamente.
     */
    @Test
    void testConstructorsAndGettersSetters() {
        // Arrange - Crear DTO usando constructor por defecto y setters
        UserDTO dto = new UserDTO();
        dto.setUid("uid");
        dto.setUsername("username");
        dto.setDisplayName("displayName");
        dto.setBirthDate(null);
        dto.setAvatar("avatar");
        dto.setZipCode("zip");

        // Assert - Verificar que los getters devuelvan los valores correctos
        assertEquals("uid", dto.getUid());
        assertEquals("username", dto.getUsername());
        assertEquals("displayName", dto.getDisplayName());
        assertNull(dto.getBirthDate());
        assertEquals("avatar", dto.getAvatar());
        assertEquals("zip", dto.getZipCode());

        // Arrange - Crear DTO usando constructor con parámetros
        UserDTO dto2 = new UserDTO("uid2", "displayName2", "username2", null, "avatar2", "zip2");

        // Assert - Verificar que los getters devuelvan los valores correctos
        assertEquals("uid2", dto2.getUid());
        assertEquals("username2", dto2.getUsername());
        assertEquals("displayName2", dto2.getDisplayName());
        assertNull(dto2.getBirthDate());
        assertEquals("avatar2", dto2.getAvatar());
        assertEquals("zip2", dto2.getZipCode());
    }
}
