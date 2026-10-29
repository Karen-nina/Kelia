package ar.com.kelia.domain.repository;

import ar.com.kelia.domain.model.TokenML;

import java.util.Optional;

public interface TokenMLRepository {
    void save(TokenML token);
    Optional<TokenML> findLatestValidToken();
}
