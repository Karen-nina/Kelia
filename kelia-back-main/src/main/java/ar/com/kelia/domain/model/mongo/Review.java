package ar.com.kelia.domain.model.mongo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class Review {

    private UserMongo user;
    private int rating;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));

    public Review() {
    }

    public Review(UserMongo user, int rating, String comment) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }
}
