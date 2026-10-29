package ar.com.kelia.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenMLDTO {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    private String scope;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
