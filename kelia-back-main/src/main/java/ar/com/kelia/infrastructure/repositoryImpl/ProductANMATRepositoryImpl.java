package ar.com.kelia.infrastructure.repositoryImpl;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.repository.ProductANMATRepository;
import ar.com.kelia.infrastructure.repositoryImpl.jpa.JpaProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductANMATRepositoryImpl implements ProductANMATRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaProductRepository jpaRepository;

    public ProductANMATRepositoryImpl(JpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void saveAll(List<ProductANMAT> products) {
        jpaRepository.saveAll(products);
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public List<ProductANMAT> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<ProductANMAT> getSimilarity(String brand, String des){
        double salesSim = 0.1;
        return jpaRepository.findSimilarProducts(brand, des, salesSim);
    }

    @Override
    public List<ProductANMAT> searchByType(String type)
    {
        return jpaRepository.findByProductTypeContainingIgnoreCaseOrderBySalesName(type);
    }
    @Override
    public List<ProductANMAT> findBySalesNameIgnoreAccent(String keyword) {
        return jpaRepository.findBySalesNameIgnoreAccent(keyword);
    }

    @Override
    public List<ProductANMAT> searchByTermFlexible(String term) {
        String[] words = term.trim().split("\\s+");

        StringBuilder sql = new StringBuilder("SELECT * FROM productANMAT WHERE ");
        for (int i = 0; i < words.length; i++) {
            if (i > 0) sql.append(" AND ");
            sql.append("(");
            sql.append("unaccent(sales_name) ILIKE :word").append(i);
            sql.append(" OR ");
            sql.append("unaccent(brand) ILIKE :word").append(i);
            sql.append(")");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), ProductANMAT.class);
        for (int i = 0; i < words.length; i++) {
            query.setParameter("word" + i, "%" + words[i] + "%");
        }

        return query.getResultList();
    }
}
