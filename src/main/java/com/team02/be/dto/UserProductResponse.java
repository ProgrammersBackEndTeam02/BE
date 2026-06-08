package com.team02.be.dto;

import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.team02.be.entity.Product;

@Getter
@Schema(description = "상품 응답 DTO")
public class ProductResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "상품명", example = "에티오피아 예가체프")
    private String productName;

    // ⚠️ boolean이 아닌 Boolean (wrapper) 사용
    // → Lombok이 isDecaf() 대신 getIsDecaf()를 생성
    // → Jackson이 JSON 키를 "decaf"가 아닌 "isDecaf"로 직렬화
    @Schema(description = "디카페인 여부")
    private Boolean isDecaf;

    // ⚠️ 동일하게 Boolean wrapper 사용
    @Schema(description = "산미 여부")
    private Boolean acidity;

    @Schema(description = "로스팅 레벨", example = "LIGHT / MEDIUM / DARK")
    private Product.RoastingLevel roastingLevel;



    @Schema(description = "가격", example = "25000")
    private int productPrice;

    @Schema(description = "재고", example = "50")
    private int stock;

    @Schema(description = "상품 설명")
    private String description;

    @Schema(description = "썸네일 이미지 URL")
    private String thumbnailImageUrl;

    @Schema(description = "상세 이미지 URL")
    private String detailPageImageUrl;

    // Entity → DTO 변환 책임은 DTO가 갖는 것이 깔끔
    public static ProductResponse from(Product product) {
        ProductResponse response = new ProductResponse();

        response.id = product.getId();
        response.productName = product.getProductName();
        response.isDecaf = product.isDecaf();
        response.roastingLevel = product.getRoastingLevel();
        response.acidity = product.isAcidity();
        response.productPrice = product.getProductPrice();
        response.stock = product.getStock();
        response.description = product.getDescription();
        response.thumbnailImageUrl = product.getThumbnailImageUrl();
        response.detailPageImageUrl = product.getDetailPageImageUrl();

        return response;
    }
}