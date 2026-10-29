package ar.com.kelia.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMeDTO {
    private String displayName;
    private String username;
    private String avatar;
    private Date birthDate;
    private String zipCode;
    private boolean isActive;
}
