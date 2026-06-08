package com.team02.be.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// 장바구니 상품 등록 요청 DTO
public record CartItemAddRequest(

        @Schema(description = "장바구니에 담을 상품 ID", example = "1")
        @NotNull(message = "상품 ID는 필수입니다.")
        // 장바구니에 담을 상품 ID
        Long productId,

        @Schema(description = "장바구니에 담을 상품 수량", example = "2")
        // 1개 이상만 허용함
        @Positive(message = "수량은 1개 이상이어야 합니다.")
        // 장바구니에 담을 상품 수량
        int quantity

) {
}