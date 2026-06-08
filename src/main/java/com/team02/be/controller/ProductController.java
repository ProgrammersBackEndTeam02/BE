package com.team02.be.controller;

import com.team02.be.dto.ProductResponse;
import com.team02.be.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품(Product) API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    // 상품 단건 조회 API
    @GetMapping("/api/products/{id}")
    @Operation(summary = "상품 단건 조회", description = "상품 ID로 상품 정보를 조회합니다.")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.findProduct(id);
    }

    /**
     * 상품 삭제 API
     *
     * [요청] DELETE /api/admin/products/{id}
     * [응답] 204 No Content : 삭제 성공
     *        404 Not Found  : 해당 id의 상품이 존재하지 않음
     */
    @DeleteMapping("/api/admin/products/{id}")
    @Operation(tags = {"Admin"}, summary = "상품 삭제", description = "관리자가 상품 ID로 상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "삭제할 상품 ID") @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
