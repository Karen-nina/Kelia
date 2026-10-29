package ar.com.kelia.infrastructure.repositoryImpl.mongo;

import ar.com.kelia.domain.model.mongo.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoPlaceRepository extends MongoRepository<Place, String> {
    List<Place> findByNameContainingIgnoreCase(String name);
}
