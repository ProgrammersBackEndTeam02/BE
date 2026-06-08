package com.team02.be.controller;

import com.team02.be.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품(Product) API 컨트롤러
 *
 * @RestController  : HTTP 요청을 받아 JSON 형태로 응답하는 컨트롤러임을 선언한다.
 * @RequestMapping  : 이 컨트롤러의 기본 URL 주소를 설정한다. (/api/admin/products)
 * @RequiredArgsConstructor : final 필드(productService)를 생성자로 자동 주입해준다.
 * @Tag             : Swagger UI에서 이 컨트롤러의 그룹명과 설명을 표시한다.
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 관리 API")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 삭제 API
     *
     * [요청] DELETE /api/admin/products/{id}
     * [응답] 204 No Content : 삭제 성공 (반환할 데이터 없음)
     *        404 Not Found  : 해당 id의 상품이 존재하지 않음
     *
     * @DeleteMapping  : HTTP DELETE 메서드 요청을 이 메서드가 처리한다.
     * @PathVariable   : URL 경로의 {id} 값을 파라미터로 받는다.
     *                   예) DELETE /api/admin/products/1 → id = 1
     *
     * Swagger 문서화 어노테이션:
     * @Operation      : Swagger UI에 표시될 API 제목과 설명
     * @ApiResponses   : 각 응답 코드별 설명
     * @Parameter      : 파라미터 설명
     *
     * @param id 삭제할 상품의 id (URL 경로에서 추출)
     * @return 204 No Content (삭제 성공 시 반환할 본문 없음)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "상품 삭제", description = "관리자가 상품 ID로 상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "삭제할 상품 ID") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}