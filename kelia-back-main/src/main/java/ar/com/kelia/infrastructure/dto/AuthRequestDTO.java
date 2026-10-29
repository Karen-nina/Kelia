package ar.com.kelia.infrastructure.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String idToken; // token de Firebase que da el frontend
}
