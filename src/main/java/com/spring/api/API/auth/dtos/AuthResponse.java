package com.spring.api.API.auth.dtos;

public record AuthResponse (
    String access_token,
    String refresh_token
){}
