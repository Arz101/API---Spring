package com.spring.api.API.models.DTOs.Posts;

public record LikedBy(
        Long postId,
        String username,
        Long userId
) {
}
