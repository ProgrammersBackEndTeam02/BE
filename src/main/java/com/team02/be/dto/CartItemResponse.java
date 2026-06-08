package com.team02.be.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// 장바구니 상품 응답 DTO
public record CartItemResponse(

        @Schema(description = "장바구니 상품 ID", example = "1")
        // 장바구니 상품 ID
        // 수량 수정이나 삭제 시 사용할 수 있음
        Long cartItemId,

        @Schema(description = "상품 ID", example = "3")
        // 상품 상세 페이지 이동 시 사용할 상품 ID
        Long productId,

        @Schema(description = "상품명", example = "콜롬비아 수프리모")
        // 상품명
        String productName,

        @Schema(description = "상품 썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
        // 상품 썸네일 이미지 URL
        String thumbnailImageUrl,

        @Schema(description = "상품 가격", example = "10000")
        // 상품 가격
        int productPrice,

        @Schema(description = "장바구니 상품 수량", example = "2")
        // 장바구니에 담긴 수량
        int quantity,

        @Schema(description = "상품별 총 금액", example = "20000")
        // 상품별 총 금액
        // 상품 가격 * 수량
        int itemTotalPrice

) {
}