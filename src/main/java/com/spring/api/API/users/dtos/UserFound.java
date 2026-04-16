package com.spring.api.API.users.dtos;

public record UserFound(
        Long id,
        String username,
        String avatarUrl
) {
}
