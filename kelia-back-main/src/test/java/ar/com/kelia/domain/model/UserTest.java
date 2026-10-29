package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithUidOnly() {
        String uid = "ABC123";
        User user = new User(uid);

        assertEquals(uid, user.getUid());
        assertNull(user.getDisplayName());
        assertNull(user.getUsername());
        assertNull(user.getAvatar());
        assertFalse(user.isActive());
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        User user = new User();
        user.setUid("XYZ789");
        user.setDisplayName("Juan Pérez");
        user.setUsername("jperez");
        user.setAvatar("avatar.jpg");
        user.setBirthDate(new Date());
        user.setZipCode("1754");
        user.setActive(true);

        assertEquals("XYZ789", user.getUid());
        assertEquals("Juan Pérez", user.getDisplayName());
        assertEquals("jperez", user.getUsername());
        assertEquals("avatar.jpg", user.getAvatar());
        assertEquals("1754", user.getZipCode());
        assertTrue(user.isActive());
        assertNotNull(user.getBirthDate());
    }

    @Test
    void testDefaultConstructorCreatesEmptyUser() {
        User user = new User();
        assertNull(user.getUid());
        assertNull(user.getDisplayName());
        assertFalse(user.isActive());
    }
}