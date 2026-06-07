package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor
//@AllArgsConstructor -> 멘토링 후 사용 결정
//@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK, DB의 AUTO_INCREMENT 사용
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    // 주문 상세는 반드시 하나의 주문에 속함
    // FK: order_id
    // LAZY 로딩을 사용하여 실제 Order 정보가 필요할 때 조회
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    // 주문 상세는 반드시 하나의 상품을 참조
    // FK: product_id
    // LAZY 로딩을 사용하여 실제 Product 정보가 필요할 때 조회
    private Product product;

    @Column(nullable = false)
    // 주문 수량
    // primitive(int) 사용 → Java 레벨에서 null 저장 불가
    // DB 레벨: NOT NULL 제약조건 생성
    private int quantity;

    @Column(nullable = false)
    // 주문 당시 상품 가격
    // 상품 가격이 변경되어도 주문 기록 보존을 위해 별도 저장
    // 예) 현재 상품 가격이 15,000원 → 나중에 18,000원으로 변경되어도
    // 기존 주문 내역은 15,000원으로 유지
    private int priceAtOrder;

    @Column(nullable = false)
    // 주문 항목별 총 금액
    // quantity × priceAtOrder
    // 예) 캐냐 원두 1,000원 × 2개 = 2,000원
    // Order 전체 금액이 아니라 해당 상품 라인의 총액
    private int totalPrice;
}