package ar.com.kelia.infrastructure.repositoryImpl.jpa;

import ar.com.kelia.domain.model.TokenML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTokenMLRepository extends JpaRepository<TokenML, Long> {
    Optional<TokenML> findTopByOrderByExpiresAtDesc();
}