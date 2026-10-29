package ar.com.kelia.infrastructure.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRecipePostDTO {
    private Long id;
    private String username;
    private String displayName;
    private String avatar;

    public UserRecipePostDTO(Long userId, String username, String displayName, String avatar) {
        this.id = userId;
        this.username = username;
        this.displayName = displayName;
        this.avatar = avatar;
    }
}
