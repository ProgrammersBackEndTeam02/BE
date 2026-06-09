package com.team02.be.dto;

import com.team02.be.entity.Order;
import com.team02.be.entity.Order.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String customerEmail,
        String address,
        String zipCode,
        OrderStatus orderStatus,
        int totalPrice,
        LocalDateTime createdAt,
        LocalDate deliveryDate,
        List<OrderItemResponse> orderItems
) {
    private static LocalDate calcDeliveryDate(LocalDateTime createdAt) {
        LocalDateTime cutoff = createdAt.toLocalDate().atTime(LocalTime.of(14, 0));
        return createdAt.isBefore(cutoff)
                ? createdAt.toLocalDate().plusDays(1)
                : createdAt.toLocalDate().plusDays(2);
    }

    public static OrderResponse from(Order order, List<OrderItemResponse> orderItems) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerEmail(),
                order.getAddress(),
                order.getZipCode(),
                order.getOrderStatus(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                calcDeliveryDate(order.getCreatedAt()),
                orderItems
        );
    }

    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getCustomerEmail(),
                order.getAddress(),
                order.getZipCode(),
                order.getOrderStatus(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                calcDeliveryDate(order.getCreatedAt()),
                List.of()
        );
    }
}