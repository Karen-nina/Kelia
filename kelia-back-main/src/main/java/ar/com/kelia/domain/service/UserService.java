package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void save(User user){
        this.repository.save(user);
    }

    public boolean existsByDisplayName(String term){
        return this.repository.existsByDisplayName(term);
    }

    public Optional<User> findByUid(String uid){
        return this.repository.findByUid(uid);
    }
}
