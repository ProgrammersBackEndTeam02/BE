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

    // [미사용 결정] private int priceAtOrder;
    // 담은 시점의 가격을 저장하지 않기로 결정.
    // 이유:
    // 1) OrderItem의 priceAtOrder는 '주문 확정 시점' 가격을 박제하는 게 목적
    //    (확정 후 상품 가격이 바뀌어도 주문 내역은 보존되어야 하므로)
    // 2) 반면 장바구니는 미확정 상태 → 항상 '현재 Product 가격'을 보여주는 게 자연스러움
    // 3) 가격 표시는 product_id로 Product를 참조해 현재 가격을 가져와 계산
    // → 별도 가격 컬럼 없이 Product.productPrice를 그대로 사용

    // [미사용 결정] private int totalPrice;
    // 항목별 총액(quantity × 가격)도 저장하지 않기로 결정.
    // 이유:
    // 1) 위와 동일하게 장바구니는 미확정 상태라 금액을 고정할 이유가 없음
    // 2) (현재 Product.productPrice × quantity)로 조회 시점에 즉시 계산 가능
    // → 서비스 레이어에서 계산해 프론트로 응답

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
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