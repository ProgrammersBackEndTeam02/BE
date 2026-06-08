package com.team02.be.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

// 장바구니 전체 목록 조회 응답 DTO
public record CartResponse(

        @Schema(description = "장바구니 ID", example = "1")
        // 장바구니 ID
        Long cartId,

        @Schema(description = "장바구니 상품 목록")
        // 장바구니에 담긴 상품 목록
        List<CartItemResponse> items,

        @Schema(description = "장바구니 전체 상품 총 금액", example = "30000")
        // 장바구니 전체 상품 총 금액
        int totalPrice

) {
}