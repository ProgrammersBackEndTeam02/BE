package com.team02.be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team02.be.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 응답 DTO")
public record UserProductResponse(

        @Schema(description = "상품 ID", example = "1")
        Long id,

        @Schema(description = "상품명", example = "에티오피아 예가체프")
        String productName,

        @JsonProperty("isDecaf")
        @Schema(description = "디카페인 여부")
        Boolean isDecaf,

        @Schema(description = "산미 여부")
        Boolean acidity,

        @Schema(description = "로스팅 레벨", example = "LIGHT / MEDIUM / DARK")
        Product.RoastingLevel roastingLevel,

        @Schema(description = "가격", example = "25000")
        int productPrice,

        @Schema(description = "재고", example = "50")
        int stock,

        @Schema(description = "상품 설명")
        String description,

        @Schema(description = "썸네일 이미지 URL")
        String thumbnailImageUrl,

        @Schema(description = "상세 이미지 URL")
        String detailPageImageUrl
) {

    public static UserProductResponse from(Product product) {
        return new UserProductResponse(
                product.getId(),
                product.getProductName(),
                product.isDecaf(),
                product.isAcidity(),
                product.getRoastingLevel(),
                product.getProductPrice(),
                product.getStock(),
                product.getDescription(),
                product.getThumbnailImageUrl(),
                product.getDetailPageImageUrl()
        );
    }
}