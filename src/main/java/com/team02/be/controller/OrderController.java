package com.team02.be.controller;

import com.team02.be.dto.OrderRequest;
import com.team02.be.dto.OrderResponse;
import com.team02.be.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 주문 생성 API
    @PostMapping
    @Operation(summary = "주문 생성", description = "사용자가 상품을 주문합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "주문 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 상품")
    })
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 주문 상세 조회 API
    @GetMapping("/{id}")
    @Operation(summary = "주문 상세 조회", description = "주문 ID로 주문 상세 정보를 조회합니다.")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.findOrder(id);
    }

    // 이메일로 주문 내역 조회 API
    @GetMapping
    @Operation(summary = "이메일로 주문 내역 조회", description = "이메일을 기준으로 주문 내역을 조회합니다.")
    public List<OrderResponse> getOrdersByEmail(@RequestParam String email) {
        return orderService.findOrdersByEmail(email);
    }
}