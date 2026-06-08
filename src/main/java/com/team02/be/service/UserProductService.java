package com.team02.be.service;
import com.team02.be.dto.UserProductResponse;
import com.team02.be.entity.Product;
import java.util.List;

public interface UserProductService {

    // 세 파라미터 모두 optional (null 허용)
    List<UserProductResponse> getProducts(
            Boolean isDecaf,
            Product.RoastingLevel roastingLevel,
            Boolean acidity
    );
}