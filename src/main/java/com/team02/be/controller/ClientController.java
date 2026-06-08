package com.team02.be.controller;

import com.team02.be.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.team02.be.entity.Order;
import com.team02.be.entity.Product;
import com.team02.be.service.AdminService;

import java.util.List;

@Tag(name = "Order", description = "주문 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "주문 상태 변경", description = "주문 상태를 변경합니다.")
    @PatchMapping("/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestParam Order.OrderStatus status) {
        clientService.updateClientOrderStatus(orderId,status);
    }
}
