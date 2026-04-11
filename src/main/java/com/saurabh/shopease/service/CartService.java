package com.saurabh.shopease.service;

import com.saurabh.shopease.entity.*;
import com.saurabh.shopease.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartItem addToCart(String email, Long productId, int quantity) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        var existing = cartItemRepository.findByUserAndProductId(user, productId);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        }
        return cartItemRepository.save(
            CartItem.builder().user(user).product(product).quantity(quantity).build()
        );
    }

    public List<CartItem> getCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return cartItemRepository.findByUser(user);
    }

    public void removeItem(Long itemId) { cartItemRepository.deleteById(itemId); }

    public void clearCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        cartItemRepository.deleteByUser(user);
    }
}