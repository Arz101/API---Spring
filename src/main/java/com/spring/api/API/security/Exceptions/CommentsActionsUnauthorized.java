package com.spring.api.API.security.Exceptions;

public class CommentsActionsUnauthorized extends RuntimeException {
    public CommentsActionsUnauthorized(String message) {
        super(message);
    }
}
