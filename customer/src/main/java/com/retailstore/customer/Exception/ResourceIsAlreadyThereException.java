package com.retailstore.customer.Exception;

public class ResourceIsAlreadyThereException extends Exception{

    public ResourceIsAlreadyThereException(String msg){
        super(msg);
    }

    public ResourceIsAlreadyThereException(){
        super();
    }
}
