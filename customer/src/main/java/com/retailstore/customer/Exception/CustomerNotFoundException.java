package com.retailstore.customer.Exception;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(String msg) {
        super(msg);
    }

    public CustomerNotFoundException(){
        super();
    }
}
