package com.team02.be.service;

import com.team02.be.dto.AdminProductCreateRequest;
import com.team02.be.dto.AdminProductUpdateRequest;
import com.team02.be.entity.Product;
import com.team02.be.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    // 상품 데이터에 접근하기 위해 ProductRepository를 주입받음
    // 상품 등록, 조회, 수정에 필요한 DB 작업을 처리함
    private final ProductRepository productRepository;

    // 상품 등록 메서드
    public void createProduct(AdminProductCreateRequest request) {

        //같은 상품이 이미 DB에 있는지 확인
        if (productRepository.existsByProductName(request.productName())) {

            // 이미 같은 상품이 있을 경우 등록을 막음
            throw new IllegalArgumentException("이미 등록된 상품입니다.");
        }

        // DTO를 그대로 저장하지 않고, DTO 값을 바탕으로 Product 엔티티를 생성
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
    // 상품 구분을 위해서 long 타입의 id를 받아옴
    public void updateProduct(Long productId, AdminProductUpdateRequest request) {
        // Repository에 해당 id값을 가진 상품이 있다면
        // 수정이 가능한 상태
        Product product = productRepository.findById(productId)
                // 만약 조회가 되지 않는 경우 메세지 출력
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않았거나 없는 상품입니다."));

        // 상품 수정 시에 같은 이름이 있다면 막음
        // 상품명 수정 요청이 들어온 경우에만 중복 검사를 진행함
        // 요청 상품명이 기존 상품명과 다르고,
        // 그 요청 상품명이 이미 DB에 존재하면 수정 불가
        if (request.productName() != null && !request.productName().equals(product.getProductName()) && productRepository.existsByProductName(request.productName())) {
            // 진행을 막고 메세지 출력
            throw new IllegalArgumentException("같은 이름의 상품이 있습니다.");
        }

        // 상품 수정
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
}