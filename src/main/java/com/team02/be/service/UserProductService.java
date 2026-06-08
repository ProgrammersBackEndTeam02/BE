package com.team02.be.service;
import com.team02.be.dto.ProductResponse;
import com.team02.be.entity.Product;
import java.util.List;

public interface ProductService {

    // 세 파라미터 모두 optional (null 허용)
    List<ProductResponse> getProducts(
            Boolean isDecaf,
            Product.RoastingLevel roastingLevel,
            Boolean acidity
    );
}