package com.spring.api.API.models.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record AuthResponse (
    String access_token,
    String refresh_token
){}
