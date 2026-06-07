//BeApplication.java Setting
package com.team02.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Spring Boot 애플리케이션의 시작 지점이다.
 *
 * 이 파일을 실행해야 내장 Tomcat 서버가 켜지고,
 * Controller에 작성한 API들이 실제로 요청을 받을 수 있게 된다.
 *
 * SwaggerConfig, Controller, DTO 파일을 직접 실행하는 것이 아니라
 * 반드시 이 BeApplication.java를 실행해야 한다.
 */
@EnableJpaAuditing
/**
 JPA Auditing 기능 활성화

 @CreatedDate
 @LastModifiedDate

 가 동작하도록 Spring에 알려주는 설정

 없으면 createdAt, updatedAt 값이 자동으로 저장되지 않음

 예)
 주문 생성 시 createdAt 자동 저장
 주문 수정 시 updatedAt 자동 갱신
 **/

@SpringBootApplication
public class BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeApplication.class, args);
    }
}