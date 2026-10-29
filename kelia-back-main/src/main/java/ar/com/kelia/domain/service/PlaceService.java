package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.repository.PlaceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class PlaceService {

        private final PlaceRepository placeRepository;
        private final ObjectMapper objectMapper;

    public PlaceService(PlaceRepository placeRepository, ObjectMapper objectMapper) {
        this.placeRepository = placeRepository;
        this.objectMapper = objectMapper;
    }

        public void parseAndSavePlaces(InputStream inputStream) throws Exception {
            List<Place> places = Arrays.asList(objectMapper.readValue(inputStream,Place[].class));
            placeRepository.saveAll(places);
        }


        public List<Place> findAll(){
            return this.placeRepository.findAll();
        }


        public List<Place> findByNameContainingIgnoreCase(String name) {
            return this.placeRepository.findByNameContainingIgnoreCase(name);
        }
}
