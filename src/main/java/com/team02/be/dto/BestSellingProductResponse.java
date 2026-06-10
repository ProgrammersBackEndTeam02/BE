package com.team02.be.dto;

public record BestSellingProductResponse(
        // 응답 dto라 검증 어노테이션을 사용하지 않음
        // id는 Product 엔티티가 Long 타입이었으니 Long으로 받음
        Long productId,
        String productName,
        // int랑 다르게 객체 타입으로 null이 가능함
        Integer productPrice,
        // 둘 다 값이 어디까지 커질지 모르기 때문에
        // 더 많은 범위를 보여주는 Long 타입으로 사용
        Long totalSold,
        Long totalSalesAmount
) {
}