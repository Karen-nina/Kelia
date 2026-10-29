package ar.com.kelia.domain.repository;

import ar.com.kelia.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByDisplayName(String username);
    boolean existsByDisplayName(String username);
    Optional<User> findByUid(String uid);
}
