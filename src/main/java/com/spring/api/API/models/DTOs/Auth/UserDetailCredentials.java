package com.spring.api.API.models.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UserDetailCredentials (
    String username,
    String password,
    String status
) {}
