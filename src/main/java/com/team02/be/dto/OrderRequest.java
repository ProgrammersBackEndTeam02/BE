package com.team02.be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 주문 생성 요청 DTO
 *
 * 프론트에서 "결제하기" 클릭 시 보내는 전체 주문 데이터
 *
 * 예)
 * {
 *   "customerEmail": "user@example.com",
 *   "address": "서울시 강남구",
 *   "zipCode": "06000",
 *   "items": [
 *     { "productId": 1, "quantity": 2 },
 *     { "productId": 3, "quantity": 1 }
 *   ]
 * }
 */
@Getter
@NoArgsConstructor
public class OrderRequest {

    // 주문자 이메일
    private String customerEmail;

    // 배송 주소
    private String address;

    // 우편번호
    private String zipCode;

    // 주문 상품 목록 (상품id + 수량)
    private List<OrderItemRequest> items;
}