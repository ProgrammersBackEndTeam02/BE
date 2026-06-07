package com.team02.be.controller;


import com.team02.be.service.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller 역할을 하는 클래스
@RestController
// 필요한 생성자를 자동으로 만들어줌
@RequiredArgsConstructor
// 이 컨트롤러의 모든 API는 /api/admin/orders 경로로 시작함
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    // 관리장용 주문 서비스 로직을 주입 받음
    private final AdminOrderService adminOrderService;
}
