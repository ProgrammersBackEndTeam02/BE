package com.team02.be.service;
import com.team02.be.entity.Product;
import com.team02.be.dto.UserProductResponse;
import com.team02.be.repository.UserProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {

    private final UserProductRepository userProductRepository;

    @Override
    public List<UserProductResponse> getProducts(
            Boolean isDecaf,
            Product.RoastingLevel roastingLevel,
            Boolean acidity) {

        List<Product> products = userProductRepository.findByFilters(
                isDecaf,
                roastingLevel,
                acidity
        );

        return products.stream()
                .map(UserProductResponse::from)
                .toList();
    }
}
