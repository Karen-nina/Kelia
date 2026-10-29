package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.mongo.UserMongo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMongoTest {

    @Test
    void shouldSetAndGetUserMongoFields() {
        UserMongo user = new UserMongo();
        user.setDisplayName("Lucía Fernández");
        user.setUsername("luciaf");
        user.setAvatar("lucia.jpg");
        user.setUid("UID998877");

        assertEquals("Lucía Fernández", user.getDisplayName());
        assertEquals("luciaf", user.getUsername());
        assertEquals("lucia.jpg", user.getAvatar());
        assertEquals("UID998877", user.getUid());
    }

    @Test
    void shouldCreateEmptyUserMongo() {
        UserMongo user = new UserMongo();

        assertNull(user.getDisplayName());
        assertNull(user.getUsername());
        assertNull(user.getAvatar());
        assertNull(user.getUid());
    }
}
