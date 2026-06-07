package com.team02.be.repository;

import com.team02.be.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문(Order) DB 접근 인터페이스
 *
 * JpaRepository 상속으로 save, findById 등 기본 메서드 자동 제공
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}