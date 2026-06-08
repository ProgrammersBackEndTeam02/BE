package com.team02.be.dto;

import com.team02.be.entity.Order;

import java.time.LocalDateTime;

/**
 * 관리자 주문 목록 응답 DTO
 *
 * record 사용 이유:
 * - 불변(immutable) 객체로 값 변경 불가
 * - Getter/생성자를 자동으로 제공하여 코드 간결화
 */
public record OrderListResponse(
        Long orderId,
        String customerEmail,
        String orderStatus,
        int totalPrice,
        LocalDateTime createdAt
) {
    /**
     * Order 엔티티를 OrderListResponse DTO로 변환하는 생성자
     *
     * @param order 조회된 Order 엔티티
     */
    public OrderListResponse(Order order) {
        this(order.getId(), order.getCustomerEmail(), order.getOrderStatus().name(), order.getTotalPrice(), order.getCreatedAt());
    }
}