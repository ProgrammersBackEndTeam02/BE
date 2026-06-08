package com.team02.be.exception;

/**
 * 상품을 찾을 수 없을 때 던지는 커스텀 예외
 *
 * 존재하지 않는 상품 id로 요청이 들어오면 이 예외를 발생시킨다.
 * GlobalExceptionHandler가 이 예외를 받아서 404 응답으로 변환한다.
 *
 * RuntimeException을 상속받아 별도의 try-catch 없이 사용할 수 있다.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * @param id 존재하지 않는 상품의 id
     *           예) id가 99이면 → "상품을 찾을 수 없습니다. id: 99" 메시지 생성
     */
    public ProductNotFoundException(Long id) {
        super("상품을 찾을 수 없습니다. id: " + id);
    }
}