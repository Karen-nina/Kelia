package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.domain.repository.TokenMLRepository;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JpaTokenMLRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenMLRepositoryImpl implements TokenMLRepository {

    private final JpaTokenMLRepository jpaRepository;

    public TokenMLRepositoryImpl(JpaTokenMLRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(TokenML token) {
        TokenML entity = new TokenML(token.getAccessToken(), token.getTokenType(), token.getExpiresIn(), token.getRefreshToken());
        jpaRepository.save(entity);
    }

    @Override
    public Optional<TokenML> findLatestValidToken() {
        return jpaRepository.findTopByOrderByExpiresAtDesc();
    }

}
