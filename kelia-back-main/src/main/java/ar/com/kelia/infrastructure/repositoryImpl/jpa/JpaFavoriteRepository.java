package ar.com.kelia.infrastructure.repositoryImpl.jpa;

import ar.com.kelia.domain.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUid(String user);
}
