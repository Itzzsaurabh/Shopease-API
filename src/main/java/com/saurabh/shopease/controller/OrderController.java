package com.saurabh.shopease.controller;

import com.saurabh.shopease.entity.Order;
import com.saurabh.shopease.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(Authentication auth) {
        return orderService.placeOrder(auth.getName());
    }

    @GetMapping("/my")
    public List<Order> myOrders(Authentication auth) {
        return orderService.getMyOrders(auth.getName());
    }
}