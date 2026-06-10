package com.team02.be.controller;

import com.team02.be.dto.AdminProductCreateRequest;
import com.team02.be.dto.AdminProductUpdateRequest;
import com.team02.be.dto.BestSellingProductResponse;
import com.team02.be.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// Controller 역할을 하는 클래스
@RestController
// 필요한 생성자를 자동으로 만들어줌
@RequiredArgsConstructor
// 이 컨트롤러의 모든 API는 /api/admin/products 경로로 시작함
@RequestMapping("/api/admin/products")
@Tag(name = "Admin", description = "관리자 API")
public class AdminProductController {

    // 상품 관련 로직을 처리하기 위해 ProductService를 가져옴
    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "상품의 정보를 받아 등록합니다.")
    @PostMapping
    // @Valid -> DTO에 붙인 @NotBlank, @NotNull, @Positive, @Min 같은 검증 실행
    // @RequestBody -> 요청받은 JSON 데이터를 AdminProductCreateRequest dto로 바꾸어줌
    public void addProduct(@Valid @RequestBody AdminProductCreateRequest request) {

        // 서비스 로직을 통해 상품 생성
        productService.createProduct(request);
    }

    @Operation(summary = "상품 수정", description = "상품 ID와 수정할 정보를 받아 상품 내용을 수정합니다.")
    // 부분 수정이므로 PATCH를 사용
    // /api/admin/products/{productId} api 요청이 있을 경우 실행
    @PatchMapping("/{productId}")
    // PathVariable 를 통해 api로 받아온 productId 를 설정한 변수에 넣음
    public void updateProduct(@PathVariable Long productId, @Valid @RequestBody AdminProductUpdateRequest request) {

        // 서비스 로직을 통해 상품 정보 수정
        productService.updateProduct(productId, request);
    }

    @Operation(summary = "제일 많이 팔린 상품 조회", description = "주문 데이터를 기준으로 가장 많이 팔린 상품을 조회합니다.")
    // 판매 수량이 가장 높은 상품 정보를 반환함
    @GetMapping("/best-selling")
    public BestSellingProductResponse getBestSellingProduct() {

        // 서비스 로직을 통해 제일 많이 팔린 상품 조회해서 결과 반환
        return productService.findBestSellingProduct();
    }

}
