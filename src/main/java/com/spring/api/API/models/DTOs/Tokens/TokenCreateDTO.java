package com.spring.api.API.models.DTOs.Tokens;

import com.spring.api.API.models.User;

public record TokenCreateDTO (
    String token_hash,
    User assigned_to,
    Short token_type,
    Boolean revoked
) {}