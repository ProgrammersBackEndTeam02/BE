package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
//@AllArgsConstructor -> 멘토링 후 사용 결정
//@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK, DB가 AUTO_INCREMENT 방식으로 자동 생성
    private Long id;

    @Column(nullable = false)
    // 상품명은 필수 입력값
    // DB 레벨: NOT NULL 제약조건 생성
    private String productName;

    @Column(nullable = false)
    // 디카페인 여부
    // primitive(boolean) 사용 → Java 레벨에서 null 저장 불가
    // DB 레벨: NOT NULL 제약조건 생성
    private boolean isDecaf;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    // 로스팅 정도 (LIGHT, MEDIUM, DARK)
    // EnumType.STRING 사용 → 문자열로 저장하여 Enum 순서 변경 문제 방지
    // DB 레벨: NOT NULL 제약조건 생성
    private RoastingLevel roastingLevel;

    @Column(nullable = false)
    // 산미 여부
    // primitive(boolean) 사용 → Java 레벨에서 null 저장 불가
    // DB 레벨: NOT NULL 제약조건 생성
    private boolean acidity;

    @Column(nullable = false)
    // 상품 가격
    // primitive(int) 사용 → Java 레벨에서 null 저장 불가
    // DB 레벨: NOT NULL 제약조건 생성
    private int productPrice;

    @Column(nullable = false)
    // 재고 수량
    // primitive(int) 사용 → Java 레벨에서 null 저장 불가
    // DB 레벨: NOT NULL 제약조건 생성
    private int stock;

    @Column(columnDefinition = "TEXT")
    // 상품 설명
    // 긴 문자열 저장을 위해 TEXT 타입 사용
    private String description;

    @Column(length = 255)
    // 상품 대표 이미지 URL
    // 최대 255자까지 저장
    private String thumbnailImageUrl;

    @Column(length = 255)
    // 상품 상세페이지 이미지 URL
    // 최대 255자까지 저장
    private String detailPageImageUrl;

    public enum RoastingLevel {
        LIGHT,
        MEDIUM,
        DARK
    }
}
