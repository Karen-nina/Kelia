package ar.com.kelia.config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtConfig {
    public static DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }
}

