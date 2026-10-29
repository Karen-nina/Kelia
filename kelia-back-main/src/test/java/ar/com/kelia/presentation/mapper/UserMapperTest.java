package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.infrastructure.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testToDTO() {
        User user = new User();
        user.setUid("123");
        user.setDisplayName("John Doe");
        user.setUsername("johndoe");
        user.setBirthDate(new Date(946684800000L)); // 2000-01-01
        user.setAvatar("/img/avatar.png");
        user.setZipCode("1234");

        UserDTO dto = UserMapper.toDTO(user);

        assertNotNull(dto);
        assertEquals("123", dto.getUid());
        assertEquals("John Doe", dto.getDisplayName());
        assertEquals("johndoe", dto.getUsername());
        assertEquals(new Date(946684800000L), dto.getBirthDate());
        assertEquals("/img/avatar.png", dto.getAvatar());
        assertEquals("1234", dto.getZipCode());
    }

    @Test
    void testToDTO_Null() {
        assertNull(UserMapper.toDTO(null));
    }

    @Test
    void testToEntity() {
        UserDTO dto = new UserDTO(
                "456",
                "Jane Doe",
                "janedoe",
                new Date(1104537600000L), // 2005-01-01
                "/img/other.png",
                "5678"
        );

        User user = UserMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals("456", user.getUid());
        assertEquals("Jane Doe", user.getDisplayName());
        assertEquals("janedoe", user.getUsername());
        assertEquals(new Date(1104537600000L), user.getBirthDate());
        assertEquals("/img/avatars/default.png", user.getAvatar());
        assertEquals("5678", user.getZipCode());
        assertTrue(user.isActive());
    }

    @Test
    void testToEntity_Null() {
        assertNull(UserMapper.toEntity(null));
    }

    @Test
    void testUpdateEntity() {
        User user = new User();
        user.setDisplayName("Old Name");
        user.setAvatar("/img/old.png");

        UserDTO dto = new UserDTO(
                "789",
                "New Name",
                "newuser",
                new Date(),
                "/img/new.png",
                "9999"
        );

        UserMapper.updateEntity(user, dto);

        assertEquals("New Name", user.getDisplayName());
        assertEquals("/img/avatars/default.png", user.getAvatar());
    }

    @Test
    void testUpdateEntity_NullUser() {
        UserMapper.updateEntity(null, new UserDTO());
        // no debería lanzar excepción
    }

    @Test
    void testUpdateEntity_NullDTO() {
        User user = new User();
        user.setDisplayName("Name");
        user.setAvatar("/img.png");

        UserMapper.updateEntity(user, null);

        // no debe cambiar nada
        assertEquals("Name", user.getDisplayName());
        assertEquals("/img.png", user.getAvatar());
    }
}
