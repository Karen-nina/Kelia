package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.repository.ProductANMATRepository;
import ar.com.kelia.domain.repository.ProductExcelRepository;
import ar.com.kelia.dto.ProductExcelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductANMATFromExcelService {

    private final ProductANMATRepository repository;
    private final ProductExcelRepository parser;

    public ProductANMATFromExcelService(ProductANMATRepository repository, ProductExcelRepository parser) {
        this.repository = repository;
        this.parser = parser;
    }
    public void importProductFromExcel(MultipartFile file) {
        repository.deleteAll();
        List<ProductExcelDTO> dtos = parser.parse(file);
        List<ProductANMAT> products = dtos.stream()
                .map(dto -> new ProductANMAT(dto.getBrand(), dto.getTradeName(), dto.getSalesName(), dto.getProductType(), dto.getStatus()))
                .toList();
        repository.saveAll(products);
    }
}
