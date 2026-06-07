package com.team02.be.service;

import com.team02.be.dto.*;
import com.team02.be.entity.Order;
import com.team02.be.entity.OrderItem;
import com.team02.be.entity.Product;
import com.team02.be.exception.ProductNotFoundException;
import com.team02.be.repository.OrderItemRepository;
import com.team02.be.repository.OrderRepository;
import com.team02.be.repository.ProductRepository;
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
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 생성
     *
     * 처리 흐름:
     * 1. 요청의 각 상품 항목에 대해 상품이 존재하는지 확인
     * 2. 각 항목의 금액 계산 (priceAtOrder × quantity)
     * 3. 전체 총액 계산
     * 4. Order 저장
     * 5. 각 OrderItem 저장
     * 6. 저장된 Order를 응답 DTO로 변환하여 반환
     *
     * @param request 주문 생성 요청 데이터 (이메일, 주소, 상품목록)
     * @return 생성된 주문 정보 (주문id, 상태, 총액, 생성시각)
     * @throws ProductNotFoundException 요청한 상품이 존재하지 않을 경우
     */
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        // 1. 총 주문 금액 계산
        // 각 상품의 현재 가격 × 수량을 합산
        int totalPrice = 0;
        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProductId()));
            totalPrice += product.getProductPrice() * item.getQuantity();
        }

        // 2. Order 생성 및 저장
        // 주문 상태는 생성 시 항상 PROCESSING(처리중)으로 시작
        Order order = new Order(
                request.getCustomerEmail(),
                request.getAddress(),
                request.getZipCode(),
                Order.OrderStatus.PROCESSING,
                totalPrice
        );
        orderRepository.save(order);

        // 3. OrderItem 생성 및 저장
        // 각 상품에 대해 주문 당시 가격을 기록
        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProductId()));

            int priceAtOrder = product.getProductPrice();
            int itemTotalPrice = priceAtOrder * item.getQuantity();

            OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), priceAtOrder, itemTotalPrice);
            orderItemRepository.save(orderItem);
        }

        return new OrderResponse(order);
    }

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