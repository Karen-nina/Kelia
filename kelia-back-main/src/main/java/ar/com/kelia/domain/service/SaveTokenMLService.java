package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.domain.repository.TokenMLRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.time.ZoneId;

@Service
public class SaveTokenMLService {

    private final TokenMLServiceService tokenClient;
    private final TokenMLRepository repository;

    public SaveTokenMLService(TokenMLServiceService tokenClient, TokenMLRepository repository) {
        this.tokenClient = tokenClient;
        this.repository = repository;
    }

    public void saveTokenMLAdmin(String grant, String code) {
        TokenML token = tokenClient.getToken(grant, code);
        repository.save(token);
    }

    public void saveTokenML(TokenML token) {
        repository.save(token);
    }

    public TokenML getValidToken() {
        Optional<TokenML> optionalToken = repository.findLatestValidToken();

        if (optionalToken.isPresent()) {
            TokenML token = optionalToken.get();
            if (token.getExpiresAt().isAfter(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")))) {
                return token;
            } else {
                TokenML refreshedToken = tokenClient.getToken("refresh_token", token.getRefreshToken());
                saveTokenML(refreshedToken);
                return refreshedToken;
            }
        } else {
            throw new RuntimeException("No token was found.");
        }
    }

    @Scheduled(fixedRate = 3600000)
    public void checkAndRefreshToken() {
        Optional<TokenML> tokenOpt = repository.findLatestValidToken();
        if (tokenOpt.isPresent()) {
            TokenML token = tokenOpt.get();
            if (token.getExpiresAt().isBefore(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")))) {
                TokenML newToken = tokenClient.getToken("refresh_token", token.getRefreshToken());
                repository.save(newToken);
            } else {
                System.out.println("Token valid.");
            }
        } else {
            System.out.println("No token found.");
        }
    }
}
