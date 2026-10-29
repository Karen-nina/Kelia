package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPlacesFromTermUseCase {
    @Autowired
    PlaceService service;

    public List<Place> execute(String name){
        if(name == null || name.trim().isEmpty()){
            return this.service.findAll();
        }else{
            return this.service.findByNameContainingIgnoreCase(name);
        }

    }
}
