package com.spring.api.API.models.DTOs.User;

public record UserResponseDTO (
    Long id,
    String username,
    String email,
    String status
) {}
