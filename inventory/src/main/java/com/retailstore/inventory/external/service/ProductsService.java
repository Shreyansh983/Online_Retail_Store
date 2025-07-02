package com.retailstore.inventory.external.service;

import com.retailstore.inventory.VO.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductsService {

    @GetMapping("/products/v1/searchProduct/{productId}")
    public Products getProducts(@PathVariable Long productId);

    @GetMapping("/products/v1/searchProduct")
    public Products getProductByProductNamePriceCategoryAndBrand(@RequestParam Map<String, String> map);
}
