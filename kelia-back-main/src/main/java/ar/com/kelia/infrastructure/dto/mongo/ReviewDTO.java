package ar.com.kelia.infrastructure.dto.mongo;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.infrastructure.dto.UserDTO;
import ar.com.kelia.infrastructure.dto.UserRecipePostDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewDTO{
    private UserDTO user;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewDTO(UserDTO user, int rating, String comment,LocalDateTime createdAt) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.createdAt = createdAt;
    }

}
