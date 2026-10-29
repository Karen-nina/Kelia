package ar.com.kelia.domain.model.mongo;

import ar.com.kelia.domain.model.User;
import lombok.Data;
import java.time.ZoneId;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class Comment {
    private String id = UUID.randomUUID().toString();
    private UserMongo User;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
}
