package com.team02.be.service;

import com.team02.be.entity.Order;
import com.team02.be.entity.Product;
import com.team02.be.repository.OrderRepository;
import com.team02.be.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final OrderRepository orderRepository;
    private final ProductsRepository productsRepository;

    // 주문 비활성화
//    @Transactional
//    public void cancelOrder(Long orderId, Order.OrderStatus status) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + orderId));
//        order.updateOrderStatus(status);
//    }

    // 주문 상태 변경 & 주문 비활성화
    @Transactional
    public void updateAdminOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + orderId));
        order.updateOrderStatus(status);
    }

    // 상품 목록 조회
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productsRepository.findAll();
    }
}
