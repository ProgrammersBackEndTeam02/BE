package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "cart_items",
        uniqueConstraints = {
                // 같은 장바구니에 동일 상품이 중복 row로 쌓이는 것을 방지
                // 중복으로 담으면 새 row 생성이 아니라 기존 row의 quantity를 증가시키는 방식
                // (cart_id, product_id) 조합은 유일해야 함
                @UniqueConstraint(
                        name = "uk_cart_product",
                        columnNames = {"cart_id", "product_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK, DB의 AUTO_INCREMENT 사용
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    // 장바구니 항목은 반드시 하나의 장바구니에 속함
    // FK: cart_id
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    // 장바구니 항목은 반드시 하나의 상품을 참조함
    // FK: product_id
    private Product product;

    @Column(nullable = false)
    // 담은 수량
    private int quantity;

    // 장바구니 상품 생성 시 사용함
    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // 같은 상품을 다시 담을 때 기존 수량에 추가함
    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}