package com.team02.be.dto;

import com.team02.be.entity.Order;
import com.team02.be.entity.Order.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String customerEmail,
        String address,
        String zipCode,
        OrderStatus orderStatus,
        int totalPrice,
        LocalDateTime createdAt,
        List<OrderItemResponse> orderItems
) {
    public static OrderResponse from(Order order, List<OrderItemResponse> orderItems) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerEmail(),
                order.getAddress(),
                order.getZipCode(),
                order.getOrderStatus(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                orderItems
        );
    }
}