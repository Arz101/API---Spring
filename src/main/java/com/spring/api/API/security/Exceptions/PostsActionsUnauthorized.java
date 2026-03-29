package com.spring.api.API.security.Exceptions;

public class PostsActionsUnauthorized extends RuntimeException {
    public PostsActionsUnauthorized(String message) {
        super(message);
    }
}
