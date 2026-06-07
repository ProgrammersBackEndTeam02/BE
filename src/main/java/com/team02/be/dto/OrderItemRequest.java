package com.team02.be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 항목 요청 DTO
 *
 * 프론트에서 주문할 상품 하나의 정보를 담는 클래스
 * OrderRequest 안에 리스트 형태로 포함된다.
 *
 * 예) { "productId": 1, "quantity": 2 }
 */
@Getter
@NoArgsConstructor
public class OrderItemRequest {

    // 주문할 상품의 id
    private Long productId;

    // 주문 수량
    private int quantity;
}