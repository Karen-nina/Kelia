package ar.com.kelia.infrastructure.dto.mongo;

import lombok.Data;

@Data
public class CommentDTO {
    private String post_id;
    private String uid;
    private String content;
}