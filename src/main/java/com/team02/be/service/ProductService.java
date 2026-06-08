package com.team02.be.service;

import com.team02.be.dto.ProductResponse;
import com.team02.be.entity.Product;
import com.team02.be.exception.NotFoundException;
import com.team02.be.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 상품을 찾을 수 없습니다."));
        return ProductResponse.from(product);
    }
}