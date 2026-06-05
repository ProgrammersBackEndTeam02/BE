// MemberSignupRequest.java Setting
package com.team02.be.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//회원가입 요청 DTO
//
//클라이언트가 POST /members API를 호출할 때
//요청 body에 담아서 보내는 데이터를 표현한다.
//
//Swagger에서 POST /members를 테스트할 때
//아래와 같은 JSON을 입력하게 된다.
//
// {
//   "email": "test@test.com",
//   "password": "12345678",
//   "name": "홍길동"
// }
//
//record를 사용하면 getter를 직접 만들 필요 없이
//email(), password(), name() 메서드가 자동으로 생긴다.
public record MemberSignupRequest(

        //회원 이메일
        //@Schema -> Swagger UI 화면에 설명과 예시 값을 보여준다.
        //@NotBlank -> null, 빈 문자열 "", 공백 "   "을 막는다.
        //@Email -> 이메일 형식인지 검사한다.
        @Schema(description = "회원 이메일", example = "test@test.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        //회원 비밀번호
        //@NotBlank -> null, 빈 문자열, 공백만 있는 문자열을 막는다.
        //@Size(min = 8) -> 최소 8자 이상이어야 통과한다.
        @Schema(description = "회원 비밀번호", example = "12345678")
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,

        //회원 이름
        //@NotBlank -> 이름이 비어있는 것을 막는다.
        @Schema(description = "회원 이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다.")
        String name
) {
}