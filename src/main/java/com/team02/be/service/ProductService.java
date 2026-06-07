package com.team02.be.service;

import com.team02.be.exception.ProductNotFoundException;
import com.team02.be.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품(Product) 비즈니스 로직 클래스
 *
 * @Service     : 이 클래스가 비즈니스 로직을 담당하는 서비스 계층임을 Spring에 알린다.
 * @RequiredArgsConstructor : final 필드(productRepository)를 생성자로 자동 주입해준다.
 *
 * 계층 구조:
 * Controller(요청 수신) → Service(비즈니스 로직) → Repository(DB 접근)
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 삭제
     *
     * @Transactional : DB 작업 중 오류 발생 시 자동으로 롤백된다.
     *
     * 처리 흐름:
     * 1. 해당 id의 상품이 DB에 존재하는지 확인
     * 2. 없으면 ProductNotFoundException 발생 → GlobalExceptionHandler가 404로 변환
     * 3. 있으면 DB에서 삭제
     *
     * @param id 삭제할 상품의 id
     * @throws ProductNotFoundException 상품이 존재하지 않을 경우
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}