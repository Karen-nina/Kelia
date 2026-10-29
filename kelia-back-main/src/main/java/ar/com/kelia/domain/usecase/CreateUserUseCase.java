package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    @Autowired
    UserService service;

    public void execute(User user) {
        if (service.findByUid(user.getUid()).isPresent()) {
            throw new IllegalArgumentException("El uid de usuario ya está en uso.");
        }
        service.save(user);
    }
}
