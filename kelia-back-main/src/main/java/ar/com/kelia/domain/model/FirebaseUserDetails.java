package ar.com.kelia.domain.model;

import lombok.Data;

@Data
public class FirebaseUserDetails {
    private String uid;
    private String email;
    public FirebaseUserDetails(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }
}
