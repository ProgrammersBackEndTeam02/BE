package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor
//@AllArgsConstructor -> 멘토링 후 사용 결정
//@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK, DB의 AUTO_INCREMENT 사용
    private Long id;

    @Column(nullable = false)
    // 비회원 장바구니 식별용 세션 ID
    // 로그인 없이 장바구니를 유지하기 위해 사용
    // DB 레벨: NOT NULL 제약조건 생성
    private String sessionId;

    // [미사용 결정] private int totalPrice;
    // 장바구니 총액은 DB에 저장하지 않고 조회 시점에 계산하기로 결정.
    // 이유:
    // 1) 장바구니는 '주문 미확정' 상태 → 가격을 고정해 둘 필요가 없음
    // 2) 항목 추가/삭제, 수량 변경, 상품 가격 변동마다 totalPrice를
    //    동기화해야 하는 부담이 생김 (정합성 깨질 위험)
    // 3) CartItem들의 (현재 Product 가격 × quantity) 합으로 즉시 계산 가능
    // → 따라서 총액은 서비스 레이어에서 계산해 프론트로 응답
}