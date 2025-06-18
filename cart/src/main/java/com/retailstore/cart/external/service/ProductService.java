package com.retailstore.cart.external.service;

import com.retailstore.cart.VO.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductService {

    @GetMapping("/api2/searchProducts/{productId}")
    public Product getProducts(@PathVariable Long productId);
}
