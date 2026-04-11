package com.saurabh.shopease.repository;

import com.saurabh.shopease.entity.Order;
import com.saurabh.shopease.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}