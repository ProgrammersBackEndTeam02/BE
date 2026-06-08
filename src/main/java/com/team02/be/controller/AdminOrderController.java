package com.team02.be.controller;

import com.team02.be.dto.AdminOrderSearchRequest;
import com.team02.be.dto.AdminOrderSearchResponse;
import com.team02.be.entity.Order;
import com.team02.be.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 주문(Order) API 컨트롤러
 *
 * 기본 URL: /api/admin/orders
 */
@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@Tag(name = "Admin Order", description = "관리자 주문 관리 API")
public class AdminOrderController {

    private final OrderService orderService;

    /**
     * 관리자 주문 목록 조회 및 필터 조회 API
     *
     * [요청 예시]
     * GET /api/admin/orders?page=0&size=10
     * GET /api/admin/orders?order_status=PENDING&page=0&size=10
     * GET /api/admin/orders?product_name=콜롬비아&page=0&size=10
     * GET /api/admin/orders?order_status=PENDING&product_name=콜롬비아&page=0&size=10
     *
     * order_status와 product_name이 없으면 전체 주문 목록을 조회하고,
     * 값이 있으면 해당 조건으로 필터링된 주문 목록을 조회함
     */
    @GetMapping
    @Operation(
            summary = "관리자 주문 목록 및 필터 조회",
            description = "관리자가 주문 목록을 페이지 단위로 조회하고, 주문 상태와 상품명으로 필터링할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public Page<AdminOrderSearchResponse> getAdminOrders(

            // 주문 상태 필터
            // 값이 없으면 주문 상태 조건 없이 조회함
            @RequestParam(name = "order_status", required = false)
            Order.OrderStatus orderStatus,

            // 상품명 필터
            // 값이 있으면 해당 문자열이 포함된 상품을 가진 주문만 조회함
            @RequestParam(name = "product_name", required = false)
            String productName,

            // page, size, sort 같은 페이징 정보를 받음
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    ) {
        // 쿼리 파라미터로 받은 필터 조건을 요청 DTO로 묶음
        AdminOrderSearchRequest request = new AdminOrderSearchRequest(orderStatus, productName);

        // Service에서 전체 조회 또는 필터 조회를 처리하고 응답 DTO로 변환함
        return orderService.getAdminOrders(request, pageable);
    }
}