package ar.com.kelia.domain.model.mongo;

import lombok.Data;

@Data
public class UserMongo {
    private String displayName;
    private String username;
    private String avatar;
    private String uid;
}
