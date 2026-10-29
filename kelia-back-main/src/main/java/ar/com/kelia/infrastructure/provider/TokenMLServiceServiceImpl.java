package ar.com.kelia.infrastructure.provider;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.domain.service.TokenMLServiceService;
import ar.com.kelia.infrastructure.dto.TokenMLDTO;
import org.springframework.stereotype.Component;

@Component
public class TokenMLServiceServiceImpl implements TokenMLServiceService {

    private final TokenMLApiClient apiClient;

    public TokenMLServiceServiceImpl(TokenMLApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public TokenML getToken(String grantType, String codeOrRefreshToken) {
        TokenMLDTO dto = apiClient.getToken(grantType, codeOrRefreshToken);
        return new TokenML(dto.getAccessToken(), dto.getTokenType(), dto.getExpiresIn(), dto.getRefreshToken());
    }
}

