package com.spring.api.API.posts.dtos;

public record LikedBy(
        Long postId,
        String username,
        Long userId
) {
}
