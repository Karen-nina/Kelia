package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.model.ProductANMAT;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {
    private final MercadoLibreProductService mlClient;
    private final ProductANMATService service;

    public SearchService(MercadoLibreProductService mlClient, ProductANMATService service) {
        this.mlClient = mlClient;
        this.service = service;
    }

    public List<Product> searchProduct(String term){
    return getProductsCertificates(term);
    }

    public List<Product> getProductsML(String term) {
        return mlClient.searchProducts(term);
    }

    public List<Product> searchRandomProduct() {
        List<String> foodTerms = List.of(
                "mermelada"
        );

        Random random = new Random();
        String randomTerm = foodTerms.get(random.nextInt(foodTerms.size()));

        return getProductsCertificates(randomTerm);
    }


    public List<Product> getProductsCertificates(String term) {
        List<Product> matchedProducts = getProductsML(term);
        List<Product> productDTOs = new ArrayList<>();
        Set<String> seenThumbnails = new HashSet<>();

        for (Product product : matchedProducts) {
            List<ProductANMAT> matches = service.filterByMatchPercentage(product.getSeller(), product.getTitle());

            for (ProductANMAT match : matches) {
                String title = match.getTradeName() != null ? match.getTradeName() : match.getSalesName();
                boolean certificate = "VIGENTE".equalsIgnoreCase(match.getStatus());

                String adjustedTitle = "NO REGISTRA".equalsIgnoreCase(title) && match.getBrand() != null
                        ? match.getBrand()
                        : title;

                Product dto = new Product(
                        adjustedTitle,
                        product.getThumbnail(),
                        product.getLink(),
                        match.getBrand() != null ? match.getBrand() : "Unknown",
                        certificate
                );

                if (seenThumbnails.add(dto.getThumbnail())) {
                    productDTOs.add(dto);
                }
            }
        }

        return productDTOs.size() > 5 ? productDTOs.subList(0, 5) : productDTOs;
    }


}
