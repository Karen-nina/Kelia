package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.Product;

import java.util.List;

public interface MercadoLibreProductService {
    List<Product> searchProducts(String term);
}
