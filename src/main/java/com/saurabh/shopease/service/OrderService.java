package com.saurabh.shopease.service;

import com.saurabh.shopease.entity.*;
import com.saurabh.shopease.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order placeOrder(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty");

        BigDecimal total = cartItems.stream()
            .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
            .user(user).totalAmount(total)
            .status("PENDING").createdAt(LocalDateTime.now()).build();
        Order saved = orderRepository.save(order);

        List<OrderItem> items = cartItems.stream().map(ci ->
            OrderItem.builder()
                .order(saved).product(ci.getProduct())
                .quantity(ci.getQuantity())
                .priceAtPurchase(ci.getProduct().getPrice())
                .build()
        ).collect(Collectors.toList());
        saved.setItems(items);
        orderRepository.save(saved);
        cartService.clearCart(email);
        return saved;
    }

    public List<Order> getMyOrders(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
}