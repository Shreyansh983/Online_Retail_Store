package com.retailstore.customer.Exception;

public class CustomerNotThereException extends Exception{
    public CustomerNotThereException(String msg) {
        super(msg);
    }

    public CustomerNotThereException(){
        super();
    }
}
