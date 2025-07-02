package com.retailstore.inventory.exception;

public class ResourceIsAlreadyThereException extends Exception {
    public ResourceIsAlreadyThereException(String message) {
        super(message);
    }
}