package com.spring.api.API.follows.dtos;

public record LoadGraph(
        Long followerId,
        Long followedId,
        String followedUsername,
        String followerUsername
) {
}
