package com.team02.be.service;

import com.team02.be.entity.Order;
import com.team02.be.entity.Product;
import com.team02.be.repository.OrderRepository;
import com.team02.be.repository.ProductsRepository;
import com.team02.be.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final OrderRepository orderRepository;
    private final ProductsRepository productsRepository;

    @Transactional
    public void updateClientOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + orderId));
        order.updateOrderStatus(status);
    }
}
