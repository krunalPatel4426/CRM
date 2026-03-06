package com.crm.redis_impl.config.exception.customException;

public class UnAuthorizedAccessxception extends RuntimeException {
    public UnAuthorizedAccessxception(String message) {
        super(message);
    }
}
