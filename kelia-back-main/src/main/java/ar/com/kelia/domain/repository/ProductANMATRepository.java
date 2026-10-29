package ar.com.kelia.domain.repository;

import ar.com.kelia.domain.model.ProductANMAT;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductANMATRepository {
    void saveAll(List<ProductANMAT> products);
    void deleteAll();
    List<ProductANMAT> findAll();
    List<ProductANMAT> getSimilarity(String brand, String des);
    List<ProductANMAT> findBySalesNameIgnoreAccent(String keyword);
    List<ProductANMAT> searchByTermFlexible(String term);
    List<ProductANMAT> searchByType(String type);
}
