package com.team02.be.dto;

import com.team02.be.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 관리자 주문 목록 응답 DTO
 *
 * 주문 목록 조회 시 각 주문의 핵심 정보만 담아 반환한다.
 * Entity를 직접 반환하지 않고 DTO로 변환하여 반환한다.
 */
@Getter
public class OrderListResponse {

    // 주문 id
    private final Long orderId;

    // 주문자 이메일
    private final String customerEmail;

    // 주문 상태 (PROCESSING, SHIPPING, DELIVERED, CANCELLED)
    private final String orderStatus;

    // 총 주문 금액
    private final int totalPrice;

    // 주문 생성 시각
    private final LocalDateTime createdAt;

    /**
     * Order 엔티티를 OrderListResponse DTO로 변환하는 생성자
     *
     * @param order 조회된 Order 엔티티
     */
    public OrderListResponse(Order order) {
        this.orderId = order.getId();
        this.customerEmail = order.getCustomerEmail();
        this.orderStatus = order.getOrderStatus().name();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();
    }
}