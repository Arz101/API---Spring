package com.spring.api.API.security.Exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String e){
        super(e);
    }
}
