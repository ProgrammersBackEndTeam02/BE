package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK, DB의 AUTO_INCREMENT 사용
    private Long id;

    @Column(nullable = false, unique = true)
    // 비회원 장바구니 식별용 토큰
    // 브라우저 쿠키에 저장된 cart_token 값과 연결됨
    private String cartToken;

    // 비회원 장바구니 생성 시 사용함
    public Cart(String cartToken) {
        this.cartToken = cartToken;
    }
}