package com.retailstore.order.exception;

public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(String msg) {
        super(msg);
    }

    public OrderNotFoundException(){
        super();
    }
}
