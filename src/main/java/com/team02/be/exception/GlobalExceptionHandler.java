package com.team02.be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 전역 예외 처리 클래스
 *
 * @RestControllerAdvice : 모든 컨트롤러에서 발생하는 예외를 한 곳에서 처리한다.
 * 예외가 발생하면 이 클래스가 자동으로 잡아서 적절한 HTTP 응답으로 변환한다.
 *
 * 흐름:
 * 컨트롤러/서비스에서 예외 발생 → GlobalExceptionHandler가 캐치 → HTTP 응답 반환
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 상품을 찾을 수 없을 때 처리
     *
     * @ExceptionHandler : 괄호 안의 예외가 발생하면 이 메서드가 실행된다.
     * ProductNotFoundException 발생 시 → 404 Not Found + 예외 메시지 반환
     *
     * @param e 발생한 예외 (메시지: "상품을 찾을 수 없습니다. id: {id}")
     * @return 404 상태코드 + 예외 메시지
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * 요청 DTO 검증 실패 시 처리
     *
     * @Valid 검증 실패 시 → 400 Bad Request + 어떤 필드가 왜 잘못됐는지 반환
     * 예) "customerEmail: 올바른 이메일 형식이 아닙니다., quantity: 수량은 1 이상이어야 합니다."
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}