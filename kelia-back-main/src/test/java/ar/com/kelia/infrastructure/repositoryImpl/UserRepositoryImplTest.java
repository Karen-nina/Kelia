package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JPAUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {
    private JPAUserRepository jpaRepository;
    private UserRepositoryImpl repository;

    @BeforeEach
    void setUp() throws Exception {
        jpaRepository = mock(JPAUserRepository.class);
        repository = new UserRepositoryImpl();
        java.lang.reflect.Field field = UserRepositoryImpl.class.getDeclaredField("repository");
        field.setAccessible(true);
        field.set(repository, jpaRepository);
    }

    @Test
    void testSave() {
        User user = new User();
        when(jpaRepository.save(user)).thenReturn(user);
        User result = repository.save(user);
        assertEquals(user, result);
        verify(jpaRepository).save(user);
    }

    @Test
    void testFindByDisplayName() {
        User user = new User();
        when(jpaRepository.findByDisplayName("test")).thenReturn(Optional.of(user));
        Optional<User> result = repository.findByDisplayName("test");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testExistsByDisplayName() {
        when(jpaRepository.existsByDisplayName("test")).thenReturn(true);
        assertTrue(repository.existsByDisplayName("test"));
    }

    @Test
    void testFindByUid() {
        User user = new User();
        when(jpaRepository.findByUid("uid1")).thenReturn(Optional.of(user));
        Optional<User> result = repository.findByUid("uid1");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}