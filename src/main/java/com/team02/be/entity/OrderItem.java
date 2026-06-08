package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int priceAtOrder;

    @Column(nullable = false)
    private int totalPrice;

    // JPA 기본 생성자
    protected OrderItem() {
    }

    // 주문 항목 생성 시 사용하는 생성자
    public OrderItem(Order order, Product product, int quantity, int priceAtOrder, int totalPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
        this.totalPrice = totalPrice;
    }
}