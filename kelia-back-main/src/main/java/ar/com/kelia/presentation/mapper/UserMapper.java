package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.infrastructure.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return new UserDTO(
                user.getUid(),
                user.getDisplayName(),
                user.getUsername(),
                user.getBirthDate(),
                user.getAvatar(),
                user.getZipCode()
        );
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setUid(dto.getUid());
        user.setDisplayName(dto.getDisplayName());
        user.setAvatar("/img/avatars/default.png");
        user.setBirthDate(dto.getBirthDate());
        user.setZipCode(dto.getZipCode());
        user.setUsername(dto.getUsername());
        user.setActive(true);
        return user;
    }

    public static void updateEntity(User user, UserDTO dto) {
        if (user == null || dto == null) return;
        user.setDisplayName(dto.getDisplayName());
        user.setAvatar("/img/avatars/default.png");
    }
}
