//MemberResponse.java Setting
package com.team02.be.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 회원 응답 DTO
 *
 * 서버가 클라이언트에게 회원 정보를 응답할 때 사용하는 DTO다.
 *
 * 회원가입 요청에는 password가 들어오지만,
 * 응답에는 password를 포함하지 않는다.
 *
 * 이유:
 * 비밀번호는 민감한 정보이므로 API 응답으로 내려주면 안 된다.
 */
public record MemberResponse(

        /**
         * 회원 고유 ID
         */
        @Schema(description = "회원 ID", example = "1")
        Long id,

        /**
         * 회원 이메일
         */
        @Schema(description = "회원 이메일", example = "test@test.com")
        String email,

        /**
         * 회원 이름
         */
        @Schema(description = "회원 이름", example = "홍길동")
        String name
) {
}