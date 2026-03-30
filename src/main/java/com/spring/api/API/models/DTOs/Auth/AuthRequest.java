package com.spring.api.API.models.DTOs.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record AuthRequest (
    @NotBlank String username,
    @NotBlank String password
) {}
