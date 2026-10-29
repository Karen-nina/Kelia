package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.service.ProductANMATService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllProductsByTypeUseCase {

    @Autowired
    private ProductANMATService service;

    public List<ProductANMAT> execute(String type){
        return service.findAllByType(type);
    }

}