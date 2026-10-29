package ar.com.kelia.infrastructure.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRecipePostDTOTest {
    @Test
    void testNoArgsConstructorAndSetters() {
        // Test básico: verificar que el constructor sin argumentos y setters funcionan
        UserRecipePostDTO dto = new UserRecipePostDTO();
        dto.setId(1L);
        dto.setUsername("user");
        dto.setDisplayName("nombre");
        dto.setAvatar("avatar.png");
        assertEquals(1L, dto.getId());
        assertEquals("user", dto.getUsername());
        assertEquals("nombre", dto.getDisplayName());
        assertEquals("avatar.png", dto.getAvatar());
    }

    @Test
    void testAllArgsConstructor() {
        // Test del constructor con todos los argumentos: verificar que asigna valores
        // correctamente
        UserRecipePostDTO dto = new UserRecipePostDTO(2L, "user2", "nombre2", "avatar2.png");
        assertEquals(2L, dto.getId());
        assertEquals("user2", dto.getUsername());
        assertEquals("nombre2", dto.getDisplayName());
        assertEquals("avatar2.png", dto.getAvatar());
    }

    @Test
    void testEqualsAndHashCode() {
        // Test de igualdad y hash code: verificar que DTOs con mismos valores son
        // iguales
        UserRecipePostDTO dto1 = new UserRecipePostDTO(3L, "u", "n", "a");
        UserRecipePostDTO dto2 = new UserRecipePostDTO(3L, "u", "n", "a");
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }
}