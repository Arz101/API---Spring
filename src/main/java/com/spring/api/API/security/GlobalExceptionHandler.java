package com.spring.api.API.security;

import com.spring.api.API.security.Exceptions.AccountException;
import com.spring.api.API.security.Exceptions.EmailException;
import com.spring.api.API.security.Exceptions.InvalidTokenException;
import com.spring.api.API.security.Exceptions.ProfilePrivateException;
import com.spring.api.API.security.Exceptions.UserAlreadyExistsException;
import com.spring.api.API.security.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> UserExistsHandleException(UserAlreadyExistsException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundHandleException(UserNotFoundException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> InvalidTokenHandleException(InvalidTokenException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler(ProfilePrivateException.class)
    public ResponseEntity<?> InvalidTokenHandleException(ProfilePrivateException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<?> EmailHandleException(EmailException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<?> AccountHandleException(AccountException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
}