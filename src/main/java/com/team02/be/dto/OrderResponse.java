package com.team02.be.dto;

import com.team02.be.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 주문 생성 응답 DTO
 *
 * 주문 생성 완료 후 프론트에 반환하는 데이터
 * Entity를 직접 반환하지 않고 DTO로 변환하여 반환한다.
 *
 * Entity를 직접 반환하지 않는 이유:
 * - 불필요한 내부 데이터 노출 방지
 * - API 응답 형태를 자유롭게 조정 가능
 */
@Getter
public class OrderResponse {

    // 생성된 주문 id
    private final Long orderId;

    // 주문 상태 (PROCESSING: 처리중)
    private final String orderStatus;

    // 총 주문 금액
    private final int totalPrice;

    // 주문 생성 시각
    private final LocalDateTime createdAt;

    /**
     * Order 엔티티를 OrderResponse DTO로 변환하는 생성자
     *
     * @param order 저장된 Order 엔티티
     */
    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus().name();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();
    }
}