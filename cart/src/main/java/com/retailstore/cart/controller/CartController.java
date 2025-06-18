package com.retailstore.cart.controller;

import com.retailstore.cart.exception.CartNotFoundException;
import com.retailstore.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api4")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/create/{customerId}")
    public ResponseEntity<?> createCart(@PathVariable Long customerId) {
        return new ResponseEntity<>(cartService.createCart(customerId), HttpStatus.CREATED);
    }

    @PostMapping("/addProduct/{customerId}/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long customerId,@PathVariable Long productId) throws CartNotFoundException {
        return new ResponseEntity<>(cartService.addCart(customerId, productId),HttpStatus.CREATED);
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCart(@PathVariable Long customerId) throws CartNotFoundException {
        return new ResponseEntity<>(cartService.searchCart(customerId),HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCart(@PathVariable Long customerId) throws CartNotFoundException {
        cartService.deleteCart(customerId);

    }

    @DeleteMapping("/empty/{customerId}")
    public ResponseEntity<?> emptyCart(@PathVariable Long customerId) throws CartNotFoundException {
        return new ResponseEntity<>(cartService.emptyCart(customerId),HttpStatus.OK);
    }

    @DeleteMapping("/removeProduct/{customerId}/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long customerId, @PathVariable Long productId) throws CartNotFoundException {
        return new ResponseEntity<>(cartService.removeProduct(customerId, productId),HttpStatus.OK);
    }
}
