package ar.com.kelia.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String title;
    private String thumbnail;
    private String link;
    private String seller;
    private boolean certificate;
}
