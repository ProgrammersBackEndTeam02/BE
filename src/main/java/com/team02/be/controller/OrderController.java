package com.team02.be.controller;

import com.team02.be.dto.OrderRequest;
import com.team02.be.dto.OrderResponse;
import com.team02.be.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문(Order) API 컨트롤러
 *
 * 기본 URL: /api/orders
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "주문 관리 API")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성 API
     *
     * [요청] POST /api/orders
     *        Body: { customerEmail, address, zipCode, items: [{productId, quantity}] }
     * [응답] 201 Created : 주문 생성 성공
     *        404 Not Found : 요청한 상품이 존재하지 않음
     *
     * @RequestBody : HTTP 요청 본문(JSON)을 OrderRequest 객체로 변환
     *
     * @param request 주문 생성 요청 데이터
     * @return 201 + 생성된 주문 정보
     */
    @PostMapping
    @Operation(summary = "주문 생성", description = "사용자가 상품을 주문합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "주문 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 상품")
    })
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}