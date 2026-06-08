package com.team02.be.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 주문 항목 요청 DTO
 *
 * record 사용 이유:
 * - 불변(immutable) 객체로 값 변경 불가
 * - Getter/생성자를 자동으로 제공하여 코드 간결화
 *
 * 예) { "productId": 1, "quantity": 2 }
 */
public record OrderItemRequest(

        @NotNull(message = "상품 ID는 필수입니다.")
        Long productId,

        @Positive(message = "수량은 1 이상이어야 합니다.")
        int quantity

) {}