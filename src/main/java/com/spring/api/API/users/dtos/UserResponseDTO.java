package com.spring.api.API.users.dtos;

public record UserResponseDTO (
    Long id,
    String username,
    String email,
    String status
) {}
