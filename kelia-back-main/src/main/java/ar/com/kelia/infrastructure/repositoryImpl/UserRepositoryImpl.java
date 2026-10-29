package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.repository.UserRepository;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JPAUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JPAUserRepository repository;

    @Override
    public User save(User user) {
        repository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByDisplayName(String username) {
        return repository.findByDisplayName(username);
    }

    @Override
    public boolean existsByDisplayName(String username) {
        return repository.existsByDisplayName(username);
    }

    @Override
    public  Optional<User> findByUid(String uid) {
        return repository.findByUid(uid);
    }
}
