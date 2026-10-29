package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.repository.PlaceRepository;
import ar.com.kelia.infrastructure.repositoryImpl.mongo.MongoPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlaceRepositoryAdapter implements PlaceRepository {

    @Autowired
    private MongoPlaceRepository repository;

    @Override
    public void save(Place place) {
        this.repository.save(place);
    }

    @Override
    public List<Place> findByNameContainingIgnoreCase(String name) {
        return this.repository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public void saveAll(List<Place> places) {
        this.repository.saveAll(places);
    }

    @Override
    public List<Place> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Place> findById(String id) {
        return repository.findById(id);
    }
}
