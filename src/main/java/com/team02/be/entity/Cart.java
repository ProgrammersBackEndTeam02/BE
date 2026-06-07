package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@Getter
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

    // JPA 기본 생성자
    protected Cart() {
    }

    // 장바구니 생성자
    public Cart(String sessionId) {
        this.sessionId = sessionId;
    }
}
