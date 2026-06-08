package com.team02.be.controller;

import com.team02.be.entity.Order;
import com.team02.be.entity.Product;
import com.team02.be.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "admin-controller", description = "관리자 API")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

//    @Operation(summary = "주문 삭제(비활성)", description = "주문을 비활성처리합니다.")
//    @PostMapping
//    public MemberResponse signup(@Valid @RequestBody MemberSignupRequest request) {
//        return AdminService.signup(request);
//    }

    @Operation(summary = "관리자 주문 상태 변경", description = "관리자가 주문 상태를 변경합니다.")
    @PostMapping("/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestParam Order.OrderStatus status) {
        adminService.updateAdminOrderStatus(orderId,status);
    }

    @Operation(summary = "관리자 상품 목록 전체 조회", description = "관리자가 상품 목록 전체를 조회합니다")
    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return adminService.findAllProducts();
    }

}
