package com.retailstore.cart.exception;

public class CartNotFoundException extends Exception{

    public CartNotFoundException(String msg) {
        super(msg);
    }

    public CartNotFoundException(){
        super();
    }
}
