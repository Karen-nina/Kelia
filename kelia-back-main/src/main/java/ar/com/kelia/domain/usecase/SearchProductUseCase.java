package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchProductUseCase {
    @Autowired
    private SearchService service;

    public List<Product> execute(String term) {
        return this.service.searchProduct(term);
    }
}
