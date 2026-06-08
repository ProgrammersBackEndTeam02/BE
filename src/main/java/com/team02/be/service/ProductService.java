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

    // 상품 데이터에 접근하기 위해 ProductRepository를 주입받음
    // 상품 등록, 조회, 수정, 삭제에 필요한 DB 작업을 처리함
    private final ProductRepository productRepository;

    // 상품 등록 메서드
    public void createProduct(AdminProductCreateRequest request) {

        // 같은 상품이 이미 DB에 있는지 확인
        if (productRepository.existsByProductName(request.productName())) {
            throw new IllegalArgumentException("이미 등록된 상품입니다.");
        }

        // DTO 값을 바탕으로 Product 엔티티를 생성함
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

        // 상품 등록
        productRepository.save(product);
    }

    // 상품 수정 메서드
    public void updateProduct(Long productId, AdminProductUpdateRequest request) {

        // 수정할 상품을 id로 조회함
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않았거나 없는 상품입니다."));

        // 상품명 수정 요청이 들어온 경우에만 중복 검사를 진행함
        if (request.productName() != null
                && !request.productName().equals(product.getProductName())
                && productRepository.existsByProductName(request.productName())) {
            throw new IllegalArgumentException("같은 이름의 상품이 있습니다.");
        }

        // null이 아닌 값만 상품 엔티티에 반영함
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

        // 삭제하려는 상품이 존재하지 않으면 예외 처리함
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }
}