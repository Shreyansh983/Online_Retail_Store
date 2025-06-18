package com.retailstore.order.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CART-SERVICE")
public interface CartService {

    @DeleteMapping("api4/empty/{customerId}")
    public ResponseEntity<?> emptyCart(@PathVariable Long customerId);
}
