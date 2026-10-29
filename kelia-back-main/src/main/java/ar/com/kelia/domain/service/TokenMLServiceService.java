package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.TokenML;

public interface TokenMLServiceService {
    TokenML getToken(String grantType, String codeOrRefreshToken);
}
