package com.team02.be.service;

import com.team02.be.dto.OrderItemResponse;
import com.team02.be.dto.OrderResponse;
import com.team02.be.entity.Order;
import com.team02.be.exception.NotFoundException;
import com.team02.be.repository.OrderItemRepository;
import com.team02.be.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;   // 추가

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;   // 추가
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 주문을 찾을 수 없습니다."));

        List<OrderItemResponse> orderItems = orderItemRepository.findByOrderId(id).stream()
                .map(OrderItemResponse::from)
                .toList();

        return OrderResponse.from(order, orderItems);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByCustomerEmail(email);

        return orders.stream()
                .map(order -> {
                    List<OrderItemResponse> orderItems = orderItemRepository.findByOrderId(order.getId()).stream()
                            .map(OrderItemResponse::from)
                            .toList();
                    return OrderResponse.from(order, orderItems);
                })
                .toList();
    }
}