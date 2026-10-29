package ar.com.kelia.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductANMAT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String tradeName;
    private String salesName;
    private String productType;
    private String status;

    public ProductANMAT(String brand, String tradeName, String salesName, String productType, String status) {
        this.brand = brand;
        this.tradeName = tradeName;
        this.salesName = salesName;
        this.productType = productType;
        this.status = status;
    }
}