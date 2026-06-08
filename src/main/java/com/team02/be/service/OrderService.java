package com.team02.be.service;

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
        for (OrderItemRequest item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException(item.productId()));
            totalPrice += product.getProductPrice() * item.quantity();
        }

        // 2. Order 생성 및 저장
        // 주문 상태는 생성 시 항상 PROCESSING(처리중)으로 시작
        Order order = new Order(
                request.customerEmail(),
                request.address(),
                request.zipCode(),
                Order.OrderStatus.PROCESSING,
                totalPrice
        );
        orderRepository.save(order);

        // 3. OrderItem 생성 및 저장
        // 각 상품에 대해 주문 당시 가격을 기록
        for (OrderItemRequest item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException(item.productId()));

            int priceAtOrder = product.getProductPrice();
            int itemTotalPrice = priceAtOrder * item.quantity();

            OrderItem orderItem = new OrderItem(order, product, item.quantity(), priceAtOrder, itemTotalPrice);
            orderItemRepository.save(orderItem);
        }

        return new OrderResponse(order);
    }

    /**
     * 주문 목록 조회 (페이징)
     *
     * 처리 흐름:
     * 1. Pageable(page, size)을 받아 해당 페이지의 주문 목록 조회
     * 2. Order 엔티티를 OrderListResponse DTO로 변환하여 반환
     *
     * Pageable 동작 방식:
     * - page=0, size=10 → 첫 번째 페이지, 10개씩 조회
     * - page=1, size=10 → 두 번째 페이지, 10개씩 조회
     *
     * @param pageable 페이지 번호, 페이지 크기 정보
     * @return 페이징된 주문 목록 (주문id, 이메일, 상태, 총액, 생성시각)
     */
    @Transactional(readOnly = true)
    public Page<OrderListResponse> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderListResponse::new);
    }
}
