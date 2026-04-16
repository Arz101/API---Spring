package com.spring.api.API.posts.dtos;

public record PostViewedDto(
        String username,
        Long userId,
        Long postId
) {}
