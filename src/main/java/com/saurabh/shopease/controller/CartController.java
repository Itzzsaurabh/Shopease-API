package com.saurabh.shopease.controller;

import com.saurabh.shopease.entity.CartItem;
import com.saurabh.shopease.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart(Authentication auth) {
        return cartService.getCart(auth.getName());
    }

    @PostMapping("/add")
    public CartItem addToCart(Authentication auth,
                               @RequestParam Long productId,
                               @RequestParam int quantity) {
        return cartService.addToCart(auth.getName(), productId, quantity);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> remove(@PathVariable Long itemId) {
        cartService.removeItem(itemId); return ResponseEntity.noContent().build();
    }
}