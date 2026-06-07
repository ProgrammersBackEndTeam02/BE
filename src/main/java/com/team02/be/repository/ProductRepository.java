package com.team02.be.repository;

import com.team02.be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 상품(Product) DB 접근 인터페이스
 *
 * JpaRepository를 상속받으면 아래 메서드들이 자동으로 생성된다.
 * 별도로 구현 코드를 작성할 필요 없이 Spring이 자동으로 만들어준다.
 *
 * 주요 제공 메서드:
 * - existsById(Long id) : 해당 id의 상품이 존재하는지 확인 (있으면 true)
 * - deleteById(Long id) : 해당 id의 상품을 DB에서 삭제
 * - findById(Long id)   : 해당 id의 상품 조회
 * - findAll()           : 전체 상품 목록 조회
 *
 * JpaRepository<Product, Long>
 * → 첫 번째 타입(Product): 어떤 엔티티를 다루는지
 * → 두 번째 타입(Long): PK(id)의 타입
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}