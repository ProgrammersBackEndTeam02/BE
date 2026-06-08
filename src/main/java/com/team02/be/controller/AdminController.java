package com.team02.be.controller;

import com.team02.be.dto.AdminOrderResponse;
import com.team02.be.entity.Order;
import com.team02.be.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Admin", description = "관리자 API")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "관리자 주문 상태 변경", description = "관리자가 주문 상태를 변경합니다.")
    @PatchMapping("/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestParam Order.OrderStatus status) {
        adminService.updateAdminOrderStatus(orderId,status);
    }

    @Operation(summary = "관리자 상품 목록 전체 조회", description = "관리자가 상품 목록 전체를 조회합니다")
    @GetMapping("/products")
    public List<AdminOrderResponse> findAllProducts() {
        return adminService.findAllProducts();
    }

}
