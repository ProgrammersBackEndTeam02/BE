package com.team02.be.repository;

import com.team02.be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// extends JpaRepository를 통해 기본 CRUD 메서드(save, findById, findAll, delete 등)를 사용할 수 있음
// <Product, Long> -> Product 엔티티를 사용할 거고 Product 엔티티의 id 타입은 Long 타입이기 때문임
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품명 중복 확인
    // 중복 된게 있을 경우 막음
    // existsBy + productName
    boolean existsByProductName(String productName);
}
