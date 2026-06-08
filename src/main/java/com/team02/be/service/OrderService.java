package com.team02.be.service;

import com.team02.be.dto.*;
import com.team02.be.entity.Order;
import com.team02.be.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    // 주문 데이터 조회를 위해 OrderRepository 주입받음
    private final OrderRepository orderRepository;

    // 관리자 주문 목록 필터 조회
    // 주문 상태와 상품명 조건을 받아 조건에 맞는 주문 목록을 조회함
    @Transactional(readOnly = true)
    public Page<AdminOrderSearchResponse> getAdminOrders(
            AdminOrderSearchRequest request,
            Pageable pageable
    ) {
        // 요청 DTO에서 주문 상태와 상품명 조건을 꺼내 Repository에 전달함
        // page, size 정보는 pageable을 통해 함께 전달함
        Page<Order> orders = orderRepository.searchAdminOrders(
                request.orderStatus(),
                request.productName(),
                pageable
        );

        // 조회된 Order 엔티티 목록을 AdminOrderSearchResponse DTO 목록으로 변환해 반환함
        return orders.map(order -> new AdminOrderSearchResponse(
                order.getId(),
                order.getCustomerEmail(),
                order.getAddress(),
                order.getZipCode(),
                order.getOrderStatus(),
                order.getTotalPrice(),
                order.getCreatedAt()
        ));
    }
}