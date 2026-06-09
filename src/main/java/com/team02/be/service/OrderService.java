package com.team02.be.service;

import com.team02.be.dto.AdminOrderSearchRequest;
import com.team02.be.dto.AdminOrderGroupResponse;
import com.team02.be.dto.AdminOrderSearchResponse;
import com.team02.be.dto.OrderItemRequest;
import com.team02.be.dto.OrderItemResponse;
import com.team02.be.dto.OrderListResponse;
import com.team02.be.dto.OrderRequest;
import com.team02.be.dto.OrderResponse;
import com.team02.be.entity.Order;
import com.team02.be.entity.OrderItem;
import com.team02.be.entity.Product;
import com.team02.be.exception.NotFoundException;
import com.team02.be.exception.ProductNotFoundException;
import com.team02.be.repository.OrderItemRepository;
import com.team02.be.repository.OrderRepository;
import com.team02.be.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 주문(Order) 비즈니스 로직 클래스
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        int totalPrice = 0;
        for (OrderItemRequest item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException(item.productId()));

            totalPrice += product.getProductPrice() * item.quantity();
        }

        Order order = new Order(
                request.customerEmail(),
                request.address(),
                request.zipCode(),
                Order.OrderStatus.PROCESSING,
                totalPrice
        );
        orderRepository.save(order);

        for (OrderItemRequest item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException(item.productId()));

            int priceAtOrder = product.getProductPrice();
            int itemTotalPrice = priceAtOrder * item.quantity();

            orderItemRepository.save(new OrderItem(order, product, item.quantity(), priceAtOrder, itemTotalPrice));
        }

        return new OrderResponse(order);
    }

    // 주문 단건 조회
    @Transactional(readOnly = true)
    public OrderResponse findOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 주문을 찾을 수 없습니다."));

        List<OrderItemResponse> orderItems = orderItemRepository.findByOrderId(id)
                .stream()
                .map(OrderItemResponse::from)
                .toList();

        return OrderResponse.from(order, orderItems);
    }

    // 이메일 기준 주문 목록 조회
    @Transactional(readOnly = true)
    public List<OrderResponse> findOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByCustomerEmail(email);

        return orders.stream()
                .map(order -> {
                    List<OrderItemResponse> orderItems = orderItemRepository.findByOrderId(order.getId())
                            .stream()
                            .map(OrderItemResponse::from)
                            .toList();

                    return OrderResponse.from(order, orderItems);
                })
                .toList();
    }

    // 관리자 주문 목록 조회
    @Transactional(readOnly = true)
    public Page<OrderListResponse> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderListResponse::new);
    }

    // 관리자 주문 목록 필터 조회
    @Transactional(readOnly = true)
    public Page<AdminOrderSearchResponse> getAdminOrders(
            AdminOrderSearchRequest request,
            Pageable pageable
    ) {
        Page<Order> orders = orderRepository.searchAdminOrders(
                request.orderStatus(),
                request.productName(),
                pageable
        );

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

    // 어드민 주문 그룹 조회 (같은 이메일+주소+배송일 기준으로 묶음)
    @Transactional(readOnly = true)
    public List<AdminOrderGroupResponse> getGroupedAdminOrders() {
        List<Order> allOrders = orderRepository.findAll();

        Map<String, List<Order>> grouped = new HashMap<>();
        for (Order order : allOrders) {
            LocalDate deliveryDate = calcDeliveryDate(order.getCreatedAt());
            String key = order.getCustomerEmail() + "|" + order.getAddress() + "|" + order.getZipCode() + "|" + deliveryDate;
            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(order);
        }

        return grouped.values().stream()
                .map(orders -> {
                    Order first = orders.get(0);
                    LocalDate deliveryDate = calcDeliveryDate(first.getCreatedAt());
                    int totalGroupPrice = orders.stream().mapToInt(Order::getTotalPrice).sum();
                    List<AdminOrderSearchResponse> orderResponses = orders.stream()
                            .sorted(Comparator.comparing(Order::getCreatedAt))
                            .map(o -> new AdminOrderSearchResponse(
                                    o.getId(), o.getCustomerEmail(), o.getAddress(),
                                    o.getZipCode(), o.getOrderStatus(), o.getTotalPrice(), o.getCreatedAt()))
                            .toList();
                    return new AdminOrderGroupResponse(
                            deliveryDate, first.getCustomerEmail(), first.getAddress(),
                            first.getZipCode(), orders.size(), totalGroupPrice, orderResponses);
                })
                .sorted(Comparator.comparing(AdminOrderGroupResponse::deliveryDate))
                .toList();
    }

    private LocalDate calcDeliveryDate(LocalDateTime createdAt) {
        LocalDateTime cutoff = createdAt.toLocalDate().atTime(LocalTime.of(14, 0));
        return createdAt.isBefore(cutoff)
                ? createdAt.toLocalDate().plusDays(1)
                : createdAt.toLocalDate().plusDays(2);
    }
}