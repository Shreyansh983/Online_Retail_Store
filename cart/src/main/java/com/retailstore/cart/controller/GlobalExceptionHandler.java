package com.retailstore.cart.controller;

import com.retailstore.cart.exception.CartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> handleCartNotFoundException(CartNotFoundException cartNotFoundException){
        return new ResponseEntity<>("Account not found!!", HttpStatus.NOT_FOUND);
    }
}
