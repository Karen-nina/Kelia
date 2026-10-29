package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService();
        // Inyectar el mock usando reflexión
        try {
            java.lang.reflect.Field repositoryField = UserService.class.getDeclaredField("repository");
            repositoryField.setAccessible(true);
            repositoryField.set(userService, userRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveCallsRepository() {
        User user = new User();
        user.setUid("123");
        user.setDisplayName("Karen");

        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testExistsByDisplayNameReturnsTrue() {
        when(userRepository.existsByDisplayName("Karen")).thenReturn(true);

        boolean exists = userService.existsByDisplayName("Karen");

        assertTrue(exists);
        verify(userRepository).existsByDisplayName("Karen");
    }

    @Test
    void testFindByUidReturnsUser() {
        User user = new User();
        user.setUid("123");
        when(userRepository.findByUid("123")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUid("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getUid());
    }
}
