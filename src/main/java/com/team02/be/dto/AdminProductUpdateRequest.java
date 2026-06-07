package com.team02.be.dto;

import com.team02.be.entity.Product;

import jakarta.validation.constraints.*;

// 관리자 상품 정보 수정 요청 DTO
// 상품 정보 수정이니 NotNull이나 NotBlank 같은 값이 꼭 들어오게 하는건 다 없앰
public record AdminProductUpdateRequest(
        String productName,
        // 부분 수정에서는 값을 보내지 않은 경우와 false로 수정하려는 경우를 구분해야 함
        // Boolean은 null을 가질 수 있어서, null이면 수정하지 않고 true/false가 들어오면 그 값으로 수정할 수 있음
        Boolean isDecaf,
        Product.RoastingLevel roastingLevel,
        //앞 이유와 동일
        Boolean acidity,
        //수정 시에 0보다 작은 값을 실수로 적을 수 있으니 Positive만 살림
        @Positive(message = "가격은 0보다 커야 합니다.")
        Integer productPrice,
        //Positive 와 같은 이유
        //-1 와 같은 이상한 값이 들어올 수 있으니 Min만 살림
        @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
        Integer stock,
        String description,
        String thumbnailImageUrl,
        String detailPageImageUrl
) {
}
