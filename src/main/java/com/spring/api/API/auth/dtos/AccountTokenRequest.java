package com.spring.api.API.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public record AccountTokenRequest (
    @NotBlank
    String token
) {}
    