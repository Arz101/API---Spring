package com.spring.api.API.auth.dtos;

import com.spring.api.API.users.User;

public record TokenCreateDTO (
    String token_hash,
    User assigned_to,
    Short token_type,
    Boolean revoked
) {}