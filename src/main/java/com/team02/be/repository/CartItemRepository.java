package com.team02.be.repository;

import com.team02.be.entity.Cart;
import com.team02.be.entity.CartItem;
import com.team02.be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 장바구니 상품 DB 접근 Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 특정 장바구니에 특정 상품이 이미 담겨 있는지 조회함
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    // 특정 장바구니에 담긴 모든 상품 목록을 조회함
    List<CartItem> findAllByCart(Cart cart);
}