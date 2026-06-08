package com.team02.be.service;

import com.team02.be.dto.AdminProductCreateRequest;
import com.team02.be.dto.AdminProductUpdateRequest;
import com.team02.be.entity.Product;
import com.team02.be.exception.ProductNotFoundException;
import com.team02.be.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록 메서드
    public void createProduct(AdminProductCreateRequest request) {
        if (productRepository.existsByProductName(request.productName())) {
            throw new IllegalArgumentException("이미 등록된 상품입니다.");
        }
        Product product = new Product(
                request.productName(),
                request.isDecaf(),
                request.roastingLevel(),
                request.acidity(),
                request.productPrice(),
                request.stock(),
                request.description(),
                request.thumbnailImageUrl(),
                request.detailPageImageUrl()
        );
        productRepository.save(product);
    }

    // 상품 수정 메서드
    public void updateProduct(Long productId, AdminProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않았거나 없는 상품입니다."));
        if (request.productName() != null && !request.productName().equals(product.getProductName()) && productRepository.existsByProductName(request.productName())) {
            throw new IllegalArgumentException("같은 이름의 상품이 있습니다.");
        }
        product.updateProduct(
                request.productName(),
                request.isDecaf(),
                request.roastingLevel(),
                request.acidity(),
                request.productPrice(),
                request.stock(),
                request.description(),
                request.thumbnailImageUrl(),
                request.detailPageImageUrl()
        );
    }

    // 상품 삭제 메서드
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}