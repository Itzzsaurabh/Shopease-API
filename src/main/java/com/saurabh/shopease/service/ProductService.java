package com.saurabh.shopease.service;

import com.saurabh.shopease.dto.ProductRequest;
import com.saurabh.shopease.entity.Product;
import com.saurabh.shopease.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest req) {
        Product p = Product.builder()
                .name(req.getName()).description(req.getDescription())
                .price(req.getPrice()).stock(req.getStock())
                .category(req.getCategory()).build();
        return productRepository.save(p);
    }

    public List<Product> getAllProducts() { return productRepository.findAll(); }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> search(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product updateProduct(Long id, ProductRequest req) {
        Product p = getProduct(id);
        p.setName(req.getName()); p.setDescription(req.getDescription());
        p.setPrice(req.getPrice()); p.setStock(req.getStock());
        p.setCategory(req.getCategory());
        return productRepository.save(p);
    }

    public void deleteProduct(Long id) { productRepository.deleteById(id); }
}