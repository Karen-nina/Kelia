package ar.com.kelia.infrastructure.repositoryImpl.jpa;

import ar.com.kelia.domain.model.ProductANMAT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductANMAT, Long> {
    @Query(value = """
        SELECT *, GREATEST(
            similarity(LOWER(brand), LOWER(:searchTerm)),
            similarity(LOWER(product_type), LOWER(:searchTerm2))
        ) AS sim
        FROM productANMAT
        WHERE GREATEST(
            similarity(LOWER(brand), LOWER(:searchTerm)),
            similarity(LOWER(product_type), LOWER(:searchTerm2))
        ) > :threshold
        ORDER BY sim DESC
        LIMIT 7
        """, nativeQuery = true)
    List<ProductANMAT> findSimilarProducts(
            @Param("searchTerm") String searchTerm,
            @Param("searchTerm2") String searchTerm2,
            @Param("threshold") double threshold
    );

    List<ProductANMAT> findByProductTypeContainingIgnoreCaseOrderBySalesName(String keyword);

    @Query(value = "SELECT * FROM productANMAT WHERE unaccent(sales_name) ILIKE %:keyword%", nativeQuery = true)
    List<ProductANMAT> findBySalesNameIgnoreAccent(@Param("keyword") String keyword);

}
