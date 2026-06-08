package com.team02.be.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * 주문 생성 요청 DTO
 *
 * record 사용 이유:
 * - 불변(immutable) 객체로 값 변경 불가
 * - Getter/생성자를 자동으로 제공하여 코드 간결화
 *
 * 예)
 * {
 *   "customerEmail": "user@example.com",
 *   "address": "서울시 강남구",
 *   "zipCode": "06000",
 *   "items": [
 *     { "productId": 1, "quantity": 2 }
 *   ]
 * }
 */
public record OrderRequest(

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String customerEmail,

        @NotBlank(message = "주소는 필수입니다.")
        String address,

        @NotBlank(message = "우편번호는 필수입니다.")
        String zipCode,

        @NotEmpty(message = "주문 상품은 1개 이상이어야 합니다.")
        @Valid
        List<OrderItemRequest> items

) {}