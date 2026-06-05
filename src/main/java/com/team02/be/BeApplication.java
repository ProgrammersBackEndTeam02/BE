//BeApplication.java Setting
package com.team02.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션의 시작 지점이다.
 *
 * 이 파일을 실행해야 내장 Tomcat 서버가 켜지고,
 * Controller에 작성한 API들이 실제로 요청을 받을 수 있게 된다.
 *
 * SwaggerConfig, Controller, DTO 파일을 직접 실행하는 것이 아니라
 * 반드시 이 BeApplication.java를 실행해야 한다.
 */
@SpringBootApplication
public class BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeApplication.class, args);
    }
}