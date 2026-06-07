package com.team02.be.controller;

import com.team02.be.dto.OrderResponse;
import com.team02.be.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "주문 상세 조회", description = "...")
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.findOrder(id);
    }

    @Operation(summary = "이메일로 주문 내역 조회", description = "...")
    @GetMapping
    public List<OrderResponse> getOrdersByEmail(@RequestParam String email) {
        return orderService.findOrdersByEmail(email);
    }
}