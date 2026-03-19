package com.spring.api.API.security.Exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String e){
        super(e);
    }
}
