package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.service.ProductANMATService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetAllProductsByTermUseCase {

    @Autowired
    private ProductANMATService service;

    public Map<String, List<Object>> execute(String term) {
        List<ProductANMAT> products = service.findAllByTerm(term);
        Set<String> seen = new HashSet<>();

        Set<String> brands = new LinkedHashSet<>();
        Set<String> type = new LinkedHashSet<>();
        Set<ProductANMAT> productsANMATResponse = new LinkedHashSet<>();

        products.stream()
                .filter(product -> {
                    String key = product.getBrand().toLowerCase() + "::" + product.getProductType().toLowerCase();
                    return seen.add(key);
                })
                .forEach(product -> {
                    brands.add(product.getBrand());
                    productsANMATResponse.add(product);
                    type.add(product.getProductType());
                });


        Map<String, List<Object>> result = new HashMap<>();
        result.put("filter-type", new ArrayList<>(type));
        result.put("filter-brand", new ArrayList<>(brands));;
        result.put("products", new ArrayList<>(productsANMATResponse));

        return result;
    }

}
