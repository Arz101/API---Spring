package com.spring.api.API.users.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO (
    @NotBlank String username,
    String password,
    String newPassword
) {}
