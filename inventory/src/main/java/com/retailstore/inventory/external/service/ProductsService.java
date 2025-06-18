package com.retailstore.inventory.external.service;

import com.retailstore.inventory.VO.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductsService {

    @GetMapping("/api2/searchProducts/{productId}")
    public Products getProducts(@PathVariable Long productId);
}
