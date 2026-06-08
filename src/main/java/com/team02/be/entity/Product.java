package com.team02.be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
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

    // JPA 기본 생성자
    protected Product() {
    }

    // 상품 생성자
    public Product(String productName, boolean isDecaf, RoastingLevel roastingLevel, boolean acidity, int productPrice, int stock, String description, String thumbnailImageUrl, String detailPageImageUrl) {
        this.productName = productName;
        this.isDecaf = isDecaf;
        this.roastingLevel = roastingLevel;
        this.acidity = acidity;
        this.productPrice = productPrice;
        this.stock = stock;
        this.description = description;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.detailPageImageUrl = detailPageImageUrl;

    }

    // null이 아닌 값들이 들어오면 해당 값을 수정
    public void updateProduct(
            String productName,
            Boolean isDecaf,
            RoastingLevel roastingLevel,
            Boolean acidity,
            Integer productPrice,
            Integer stock,
            String description,
            String thumbnailImageUrl,
            String detailPageImageUrl
    ) {
        // 사용자가 수정 요청에 포함한 값만 들어옴
        // null이 아닌 값만 현재 Product 객체의 필드에 반영함
        if (productName != null) {
            this.productName = productName;
        }

        if (isDecaf != null) {
            this.isDecaf = isDecaf;
        }

        if (roastingLevel != null) {
            this.roastingLevel = roastingLevel;
        }

        if (acidity != null) {
            this.acidity = acidity;
        }

        if (productPrice != null) {
            this.productPrice = productPrice;
        }

        if (stock != null) {
            this.stock = stock;
        }

        if (description != null) {
            this.description = description;
        }

        if (thumbnailImageUrl != null) {
            this.thumbnailImageUrl = thumbnailImageUrl;
        }

        if (detailPageImageUrl != null) {
            this.detailPageImageUrl = detailPageImageUrl;
        }
    }
}
