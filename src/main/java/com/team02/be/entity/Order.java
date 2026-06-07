package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
//@AllArgsConstructor -> 멘토링 후 사용 결정
//@Builder

// Auditing 기능을 Order 엔티티에 적용
// 생성 시간(createdAt), 수정 시간(updatedAt)을 자동으로 관리
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK, DB의 AUTO_INCREMENT 사용
    private Long id;

    @Column(nullable = false)
    // 주문자 이메일
    // DB 레벨: NOT NULL 제약조건 생성
    private String customerEmail;

    @Column(nullable = false)
    // 배송 주소
    // DB 레벨: NOT NULL 제약조건 생성
    private String address;

    @Column(nullable = false)
    // 우편번호
    // DB 레벨: NOT NULL 제약조건 생성
    private String zipCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    // 주문 상태
    // EnumType.STRING 사용 이유:
    // Enum 순서(ordinal)가 변경되어도 DB 데이터가 깨지지 않도록 문자열로 저장
    private OrderStatus orderStatus;

    @Column(nullable = false)
    // 총 주문 금액
    // primitive(int) 사용 이유:
    // Java 레벨에서 null 저장을 원천 차단
    // DB 레벨에서는 NOT NULL 제약조건 생성
    private int totalPrice;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    // 주문 생성 시각
    // save() 시 Spring이 자동으로 현재 시간을 저장
    // 생성 이후 수정되지 않도록 updatable=false 설정
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    // 주문 수정 시각
    // 엔티티 변경 후 save() 시 Spring이 자동 갱신
    private LocalDateTime updatedAt;

    // 주문 생성 시 사용하는 생성자
    // @NoArgsConstructor는 JPA 내부용, 이 생성자는 Service에서 사용
    public Order(String customerEmail, String address, String zipCode, OrderStatus orderStatus, int totalPrice) {
        this.customerEmail = customerEmail;
        this.address = address;
        this.zipCode = zipCode;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    public enum OrderStatus {
        PROCESSING, // 처리중
        SHIPPING,   // 배송중
        DELIVERED,  // 배송완료
        CANCELLED   // 취소
    }
}