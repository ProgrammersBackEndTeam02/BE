package com.team02.be.dto;

import com.team02.be.entity.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

// 관리자 새로운 상품 등록 요청 DTO
public record AdminProductCreateRequest(

        @Schema(description = "원두 이름", example = "콜롬비아 수프리모")
        @NotBlank(message = "원두 이름을 입력해주세요.")
        String productName,

        @Schema(description = "디카페인 여부", example = "false")
        boolean isDecaf,

        @Schema(description = "로스팅 정도", example = "MEDIUM")
        //enum 타입은 여러가지 값 중 하나는 와야 하기 때문에
        //NotNull로 판단해야함
        //NotBlank는 String 전용임
        @NotNull(message = "로스팅 정도를 선택해주세요.")
        Product.RoastingLevel roastingLevel,

        @Schema(description = "산미 여부", example = "false")
        boolean acidity,

        @Schema(description = "가격", example = "1000")
        @NotNull(message = "가격 정보를 입력해주세요.")
        //Positive는 0보다 커야한다는 의미
        //가격에는 Positive 가 어울림
        //0 초과
        @Positive(message = "가격은 0보다 커야합니다.")
        //필수 입력 검증을 하려면 Integer 타입이 좋음
        //int는 null이 될 수 없음
        Integer productPrice,

        @Schema(description = "재고", example = "100")
        @NotNull(message = "재고량을 입력해주세요.")
        //재고는 0이 될 수도 있으므로 Positive 보다 Min이 더 자연스러움
        //먼저 상품을 등록해놓고 품절 상태로 해두었다가 재고가 들어오면 변경할 수도 있기 때문임
        //0 이상
        @Min(value = 0, message = "등록 시에 재고는 0개 이상이어야 합니다.")
        Integer stock,

        @Schema(description = "상품 설명", example = "균형잡힌 데일리 원두입니다.")
        @NotBlank(message = "상품 설명을 입력해주세요.")
        String description,

        @Schema(description = "상품 대표 이미지", example = "https://example.com/images/thumbnail.jpg")
        @NotBlank(message = "상품 대표 이미지 URL을 입력해주세요.")
        String thumbnailImageUrl,

        @Schema(description = "상품 상세 페이지 이미지", example = "https://example.com/images/detail.jpg")
        @NotBlank(message = "상품 상세 이미지 URL을 입력해주세요.")
        String detailPageImageUrl

) {

}
