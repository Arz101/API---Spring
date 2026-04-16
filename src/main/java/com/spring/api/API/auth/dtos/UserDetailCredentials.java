package com.spring.api.API.auth.dtos;

public record UserDetailCredentials (
        Long userId,
        String username,
        String password,
        String status
) {}
