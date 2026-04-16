package com.spring.api.API.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest (
    @NotBlank String username,
    @NotBlank String password
) {}
