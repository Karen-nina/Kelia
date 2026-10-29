package ar.com.kelia.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirebaseUserDetailsTest {

    @Test
    void shouldCreateFirebaseUserWithUidAndEmail() {
        FirebaseUserDetails userDetails = new FirebaseUserDetails("UID123", "user@example.com");

        assertEquals("UID123", userDetails.getUid());
        assertEquals("user@example.com", userDetails.getEmail());
    }

    @Test
    void shouldAllowUpdatingUidAndEmail() {
        FirebaseUserDetails userDetails = new FirebaseUserDetails("UID123", "user@example.com");
        userDetails.setUid("NEWUID456");
        userDetails.setEmail("newuser@example.com");

        assertEquals("NEWUID456", userDetails.getUid());
        assertEquals("newuser@example.com", userDetails.getEmail());
    }
}
