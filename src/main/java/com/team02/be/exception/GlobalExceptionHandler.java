package com.team02.be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

// 모든 Controller에서 발생하는 예외를 공통으로 처리하는 클래스
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 사용자의 주문 조회 등에서 데이터를 찾을 수 없을 때 처리
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", 404,
                        "error", "Not Found",
                        "message", e.getMessage()
                ));
    }

    // 상품을 찾을 수 없을 때 처리
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    // @Valid 검증 실패 시 발생하는 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e) {

        // 검증 실패한 필드들의 에러 메시지를 하나의 문자열로 합침
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // 400 Bad Request 상태 코드와 함께 에러 메시지 반환
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
}