package ar.com.kelia.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String uid;
    private String displayName;
    private String username;
    private Date birthDate;
    private String avatar;
    private String zipCode;
}