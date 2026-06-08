package com.team02.be.service;

import com.team02.be.dto.AdminOrderSearchRequest;
import com.team02.be.dto.AdminOrderSearchResponse;
import com.team02.be.dto.OrderItemRequest;
import com.team02.be.dto.OrderListResponse;
import com.team02.be.dto.OrderRequest;
import com.team02.be.dto.OrderResponse;
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

/**
 * 주문(Order) 비즈니스 로직 클래스
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 생성
     *
     * 처리 흐름:
     * 1. 요청의 각 상품 항목에 대해 상품이 존재하는지 확인
     * 2. 각 항목의 금액 계산
     * 3. 전체 총액 계산
     * 4. Order 저장
     * 5. 각 OrderItem 저장
     * 6. 저장된 Order를 응답 DTO로 변환하여 반환
     */
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        // 1. 총 주문 금액 계산
        int totalPrice = 0;
        for (OrderItemRequest item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException(item.productId()));

            totalPrice += product.getProductPrice() * item.quantity();
        }

        // 2. Order 생성 및 저장
        Order order = new Order(
                request.customerEmail(),
                request.address(),
                request.zipCode(),
                Order.OrderStatus.PROCESSING,
                totalPrice
        );
        orderRepository.save(order);

        // 3. OrderItem 생성 및 저장
        for (OrderItemRequest item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException(item.productId()));

            int priceAtOrder = product.getProductPrice();
            int itemTotalPrice = priceAtOrder * item.quantity();

            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    item.quantity(),
                    priceAtOrder,
                    itemTotalPrice
            );

            orderItemRepository.save(orderItem);
        }

        return new OrderResponse(order);
    }

    /**
     * 주문 목록 조회
     *
     * page, size 값을 기준으로 전체 주문 목록을 조회함
     */
    @Transactional(readOnly = true)
    public Page<OrderListResponse> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderListResponse::new);
    }

    /**
     * 관리자 주문 목록 필터 조회
     *
     * 주문 상태와 상품명 조건을 받아 조건에 맞는 주문 목록을 조회함
     */
    @Transactional(readOnly = true)
    public Page<AdminOrderSearchResponse> getAdminOrders(
            AdminOrderSearchRequest request,
            Pageable pageable
    ) {
        // 요청 DTO에서 주문 상태와 상품명 조건을 꺼내 Repository에 전달함
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