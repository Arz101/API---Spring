package com.spring.api.API.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest (
    @NotBlank String refresh_token
) {}