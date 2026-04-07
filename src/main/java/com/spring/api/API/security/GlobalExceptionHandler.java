package com.spring.api.API.security;

import com.spring.api.API.security.Exceptions.*;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(@NonNull Exception e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> UserExistsHandleException(@NonNull UserAlreadyExistsException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundHandleException(@NonNull UserNotFoundException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> InvalidTokenHandleException(@NonNull InvalidTokenException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler(ProfilePrivateException.class)
    public ResponseEntity<?> InvalidTokenHandleException(@NonNull ProfilePrivateException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<?> EmailHandleException(@NonNull EmailException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<?> AccountHandleException(@NonNull AccountException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> PostNotFoundHandleException(@NonNull PostNotFoundException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(PostsActionsUnauthorized.class)
    public ResponseEntity<?> PostActionHandleException(@NonNull PostsActionsUnauthorized e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler(FollowNotFoundException.class)
    public ResponseEntity<?> FollowNotFoundHandleException(@NonNull FollowNotFoundException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

}