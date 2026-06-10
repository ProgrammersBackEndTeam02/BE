package com.team02.be.repository;

import com.team02.be.dto.BestSellingProductResponse;
import com.team02.be.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
    List<OrderItem> findByProductId(Long productId);

    // 가장 많이 팔린 상품 조회
    // SELECT new ... : 조회 결과를 BestSellingProductResponse DTO로 바로 변환
    // p.id : 상품 ID
    // p.productName : 상품명
    // p.productPrice : 상품 가격
    // SUM(oi.quantity) : 상품별 총 판매 수량
    // SUM(oi.totalPrice) : 상품별 총 판매 금액
    // FROM OrderItem oi : 판매 수량이 저장된 OrderItem을 기준으로 조회
    // JOIN oi.product p : 상품 정보를 함께 가져오기 위해 Product와 조인
    // GROUP BY p.id, p.productName, p.productPrice : 상품별로 묶어서 집계
    // ORDER BY SUM(oi.quantity) DESC : 많이 팔린 순서대로 정렬
    @Query("""
    SELECT new com.team02.be.dto.BestSellingProductResponse(
        p.id,
        p.productName,
        p.productPrice,
        SUM(oi.quantity),
        SUM(oi.totalPrice)
    )
    FROM OrderItem oi
    JOIN oi.product p
    GROUP BY p.id, p.productName, p.productPrice
    ORDER BY SUM(oi.quantity) DESC
    """)
    // 상품별 판매량 집계 결과를 많이 팔린 순서대로 조회
    // Pageable로 상위 N개만 가져올 수 있음
    List<BestSellingProductResponse> findBestSellingProducts(Pageable pageable);
}
