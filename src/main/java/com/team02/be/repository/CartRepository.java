package com.team02.be.repository;

import com.team02.be.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 장바구니 DB 접근 Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 쿠키에 저장된 cartToken 값으로 장바구니를 조회함
    Optional<Cart> findByCartToken(String cartToken);
}