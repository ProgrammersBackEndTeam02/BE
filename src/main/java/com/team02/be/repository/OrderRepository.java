package com.team02.be.repository;

import com.team02.be.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// extends JpaRepository를 통해 기본 CRUD 메서드(save, findById, findAll, delete 등)를 사용할 수 있음
// <Order, Long> -> Order 엔티티를 사용할 거고 Order 엔티티의 id 타입은 Long 타입이기 때문임
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 사용자 이메일로 주문 목록 조회
    List<Order> findByCustomerEmail(String customerEmail);

    // 오늘 오후 2시 이전에 같은 이메일/주소로 생성된 합산 가능한 주문 조회
    @Query("""
        SELECT o FROM Order o
        WHERE o.customerEmail = :email
          AND o.address = :address
          AND o.zipCode = :zipCode
          AND o.orderStatus = :status
          AND o.createdAt >= :startOfDay
          AND o.createdAt < :cutoff
        """)
    Optional<Order> findMergableOrder(
            @Param("email") String email,
            @Param("address") String address,
            @Param("zipCode") String zipCode,
            @Param("status") Order.OrderStatus status,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("cutoff") LocalDateTime cutoff
    );

    // 주문 totalPrice 누적 업데이트
    @Modifying
    @Query("UPDATE Order o SET o.totalPrice = o.totalPrice + :amount WHERE o.id = :id")
    void addTotalPrice(@Param("id") Long id, @Param("amount") int amount);

    // 직접 작성한 JPQL로 관리자 주문 목록을 필터 조회
    // SELECT DISTINCT o -> 중복을 제거하고 Order 객체 o를 조회
    // FROM OrderItem oi -> OrderItem 엔티티를 기준으로 조회를 시작하고, 그것을 oi라고 부름
    // JOIN oi.order o -> oi의 order 필드를 타고 Order 엔티티로 이동하고, 그 Order를 o라고 부름
    // JOIN oi.product p -> oi의 product 필드를 타고 Product 엔티티로 이동하고, 그 Product를 p라고 부름
    // WHERE 조건 -> orderStatus가 null이면 상태 조건을 무시하고, 값이 있으면 해당 상태의 주문만 조회
    // productName이 null이면 상품명 조건을 무시하고, 값이 있으면 해당 글자가 포함된 상품을 가진 주문만 조회
    // CONCAT('%', :productName, '%') -> 앞뒤에 %를 붙여 포함 검색을 하기 위한 문자열 생성
    // :orderStatus, :productName -> Repository 메서드 매개변수 값을 JPQL 안에서 사용하는 자리
    @Query("""
        SELECT DISTINCT o
        FROM OrderItem oi
        JOIN oi.order o
        JOIN oi.product p
        WHERE (:orderStatus IS NULL OR o.orderStatus = :orderStatus)
          AND (:productName IS NULL OR p.productName LIKE CONCAT('%', :productName, '%'))
        """)
    Page<Order> searchAdminOrders(
            // JPQL의 :orderStatus와 연결
            @Param("orderStatus") Order.OrderStatus orderStatus,

            // JPQL의 :productName과 연결
            @Param("productName") String productName,

            // page, size, sort 정보를 담는 페이징 객체
            Pageable pageable
    );
}