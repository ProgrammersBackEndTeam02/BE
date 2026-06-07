//SwaggerConfig.java Setting
package com.team02.be.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 설정 클래스
 *
 * 이 클래스는 Swagger UI 화면 상단에 표시되는
 * API 문서의 제목, 설명, 버전 정보를 설정한다.
 *
 * 주의:
 * 이 클래스는 API 주소를 만드는 Controller가 아니다.
 * 실제 API는 @RestController가 붙은 Controller 클래스에서 만든다.
 *
 * 즉,
 * SwaggerConfig = Swagger 문서 설정
 * MemberController = 실제 API 생성
 */
@Configuration
public class SwaggerConfig {

    /**
     * Swagger UI에 표시될 OpenAPI 기본 정보를 등록한다.
     *
     * 이 Bean을 등록하면 Swagger UI 화면 상단에
     * title, description, version 정보가 표시된다.
     *
     * @return Swagger 문서 기본 정보
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BE Swagger API")
                        .description("Spring Boot Swagger 테스트용 API 문서입니다.")
                        .version("v1.0.0"));
    }
}