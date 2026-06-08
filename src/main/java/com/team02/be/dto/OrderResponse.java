package com.team02.be.dto;

import com.team02.be.entity.Order;

import java.time.LocalDateTime;

/**
 * 주문 생성 응답 DTO
 *
 * record 사용 이유:
 * - 불변(immutable) 객체로 값 변경 불가
 * - Getter/생성자를 자동으로 제공하여 코드 간결화
 *
 * Entity를 직접 반환하지 않는 이유:
 * - 불필요한 내부 데이터 노출 방지
 * - API 응답 형태를 자유롭게 조정 가능
 */
public record OrderResponse(
        Long orderId,
        String orderStatus,
        int totalPrice,
        LocalDateTime createdAt
) {
    /**
     * Order 엔티티를 OrderResponse DTO로 변환하는 생성자
     *
     * @param order 저장된 Order 엔티티
     */
    public OrderResponse(Order order) {
        this(order.getId(), order.getOrderStatus().name(), order.getTotalPrice(), order.getCreatedAt());
    }
}