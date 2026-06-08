package com.team02.be.dto;

import com.team02.be.entity.Order;

// 관리자 주문 목록 필터 조회 요청 DTO
public record AdminOrderSearchRequest(

        // 주문 상태로 필터링
        // 값이 없으면 주문 상태 조건 없이 조회
        // PENDING, PROCESSING, SHIPPING, DELIVERED, CANCELLED 중 하나
        Order.OrderStatus orderStatus,

        // 주문에 포함된 상품명으로 필터링
        // 값이 없으면 상품명 조건 없이 조회
        String productName

) {
}
