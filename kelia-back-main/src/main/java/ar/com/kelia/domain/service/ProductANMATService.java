package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.repository.ProductANMATRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductANMATService {
    private final ProductANMATRepository productRepository;

    public ProductANMATService(ProductANMATRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductANMAT> filterByMatchPercentage(String brand, String des) {
        return productRepository.getSimilarity(brand, des);
    }

    public List<ProductANMAT> findAllByTerm(String term){
        return productRepository.searchByTermFlexible(term);
    }

    public List<ProductANMAT> findAllByType(String type){
        return productRepository.searchByType(type);
    }
}
