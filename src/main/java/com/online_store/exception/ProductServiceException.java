package com.online_store.exception;

public class ProductServiceException extends RuntimeException {
    public ProductServiceException(String message) {
        super(message);
    }

    public ProductServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
