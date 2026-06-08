package com.team02.be.dto;

import com.team02.be.entity.Order;

import java.time.LocalDateTime;

// 관리자 주문 목록 필터 조회 응답 DTO
public record AdminOrderSearchResponse(

        // 주문 번호
        Long orderId,

        // 주문자 이메일
        String customerEmail,

        // 배송 주소
        String address,

        // 우편 번호
        String zipCode,

        // 배송 진행 상태
        Order.OrderStatus orderStatus,

        // 총 주문 금액
        int totalPrice,

        // 주문한 시각
        LocalDateTime createdAt
) {
}
