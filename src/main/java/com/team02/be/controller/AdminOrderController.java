package com.team02.be.controller;

import com.team02.be.dto.AdminOrderSearchRequest;
import com.team02.be.dto.AdminOrderSearchResponse;
import com.team02.be.entity.Order;
import com.team02.be.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 관리자 주문 관련 API를 처리하는 컨트롤러임
// 관리자 주문 목록 조회, 주문 상태 필터링, 상품명 필터링 요청을 처리함
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/orders")
@Tag(name = "Admin Order", description = "관리자 주문 관리 API")
public class AdminOrderController {

    // 주문 관련 비즈니스 로직을 처리하기 위해 OrderService를 주입받음
    // 실제 주문 조회 로직은 Controller가 직접 처리하지 않고 Service에 위임함
    private final OrderService orderService;

    // 관리자 주문 목록 필터 조회 API임
    // order_status, product_name, page, size 값을 쿼리 파라미터로 받을 수 있음
    @GetMapping
    @Operation(
            summary = "관리자 주문 필터 조회",
            description = "주문 상태와 상품명으로 관리자 주문 목록을 필터링하여 조회합니다."
    )
    public Page<AdminOrderSearchResponse> getAdminOrders(

            // URL 쿼리 파라미터 order_status 값을 받음
            // required = false 이므로 값이 없어도 요청 가능함
            // 값이 없으면 주문 상태 조건 없이 조회함
            @RequestParam(name = "order_status", required = false)
            Order.OrderStatus orderStatus,

            // URL 쿼리 파라미터 product_name 값을 받음
            // required = false 이므로 값이 없어도 요청 가능함
            // 값이 있으면 해당 문자열이 포함된 상품을 가진 주문만 조회함
            @RequestParam(name = "product_name", required = false)
            String productName,

            // page, size, sort 같은 페이징 정보를 받음
            // Spring Data가 page, size 값을 자동으로 Pageable 객체에 담아줌
            Pageable pageable
    ) {
        // 쿼리 파라미터로 받은 orderStatus와 productName을 요청 DTO로 묶음
        // Controller에서 받은 값을 Service로 넘기기 좋은 형태로 정리함
        AdminOrderSearchRequest request = new AdminOrderSearchRequest(orderStatus, productName);

        // Service에 필터 조건과 페이징 정보를 전달함
        // Service에서 Repository 조회 후 응답 DTO로 변환한 결과를 반환함
        return orderService.getAdminOrders(request, pageable);
    }
}