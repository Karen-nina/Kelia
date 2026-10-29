package ar.com.kelia.domain.repository;

import ar.com.kelia.domain.model.mongo.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {
    void save(Place place);
    List<Place> findByNameContainingIgnoreCase(String name);
    void saveAll(List<Place> places);
    List<Place> findAll();
    Optional<Place> findById(String id);

}
