package ar.com.kelia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductExcelDTO {
    private String brand;
    private String tradeName;
    private String salesName;
    private String productType;
    private String status;

    public ProductExcelDTO() {
    }

    public ProductExcelDTO(String status, String productType, String salesName, String tradeName, String brand) {
        this.status = status;
        this.productType = productType;
        this.salesName = salesName;
        this.tradeName = tradeName;
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}
