package com.team02.be.controller;

import com.team02.be.dto.OrderListResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * 주문 목록 조회 API (페이징)
     *
     * [요청] GET /api/admin/orders?page=0&size=10
     * [응답] 200 OK : 주문 목록 반환
     *
     * @PageableDefault : page, size를 쿼리 파라미터로 받는다.
     *                    기본값: page=0, size=10
     *                    예) ?page=0&size=10 → 첫 번째 페이지, 10개씩
     *
     * Page 응답에 포함된 정보:
     * - content       : 주문 목록 데이터
     * - totalElements : 전체 주문 수
     * - totalPages    : 전체 페이지 수
     * - number        : 현재 페이지 번호
     * - size          : 페이지당 항목 수
     *
     * @param pageable 페이지 정보 (page, size)
     * @return 200 + 페이징된 주문 목록
     */
    @GetMapping
    @Operation(summary = "주문 목록 조회", description = "관리자가 전체 주문 목록을 페이지 단위로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<Page<OrderListResponse>> getOrders(
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(pageable));
    }
}