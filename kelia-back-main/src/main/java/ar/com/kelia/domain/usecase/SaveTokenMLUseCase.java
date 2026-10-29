package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.service.SaveTokenMLService;
import org.springframework.stereotype.Service;

@Service
public class SaveTokenMLUseCase {

    private final SaveTokenMLService service;

    public SaveTokenMLUseCase(SaveTokenMLService service) {
        this.service = service;
    }

    public void execute(String grant, String code) {
        this.service.saveTokenMLAdmin(grant, code);
    }
}

