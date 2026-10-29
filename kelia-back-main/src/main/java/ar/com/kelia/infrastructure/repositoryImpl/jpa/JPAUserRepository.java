package ar.com.kelia.infrastructure.repositoryImpl.jpa;

import ar.com.kelia.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JPAUserRepository extends JpaRepository<User, String> {
    Optional<User> findByDisplayName(String displayName);
    boolean existsByDisplayName(String displayName);
    Optional<User> findByUid(String uid);
}
