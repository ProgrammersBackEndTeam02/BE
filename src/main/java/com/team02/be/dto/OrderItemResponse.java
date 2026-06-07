package com.team02.be.dto;

import com.team02.be.entity.OrderItem;

public record OrderItemResponse(
        Long productId,
        String productName,
        int quantity,
        int priceAtOrder,
        int totalPrice
) {
    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getProductName(),
                orderItem.getQuantity(),
                orderItem.getPriceAtOrder(),
                orderItem.getTotalPrice()
        );
    }
}