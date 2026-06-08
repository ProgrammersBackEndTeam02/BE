package com.team02.be.service;
import com.team02.be.entity.Product;
import com.team02.be.dto.ProductResponse;
import com.team02.be.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getProducts(
            Boolean isDecaf,
            Product.RoastingLevel roastingLevel,
            Boolean acidity) {

        List<Product> products = productRepository.findByFilters(
                isDecaf,
                roastingLevel,
                acidity
        );

        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
