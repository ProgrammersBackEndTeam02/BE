package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;

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
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK, DB의 AUTO_INCREMENT 사용
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    // 장바구니 항목은 반드시 하나의 장바구니에 속함
    // FK: cart_id
    // LAZY 로딩을 사용하여 실제 Cart 정보가 필요할 때 조회
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    // 장바구니 항목은 반드시 하나의 상품을 참조
    // FK: product_id
    // LAZY 로딩을 사용하여 실제 Product 정보가 필요할 때 조회
    private Product product;

    @Column(nullable = false)
    // 담은 수량
    // 같은 상품을 다시 담으면 이 값을 증가시킴 (위 유니크 제약과 연계)
    // primitive(int) 사용 → Java 레벨에서 null 저장 불가
    // DB 레벨: NOT NULL 제약조건 생성
    private int quantity;

    // JPA 기본 생성자
    protected CartItem() {
    }

    // 장바구니 상품 생성자
    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
}
