package com.spring.api.API.likes.Likes;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record LikesId (
    Long userId,
    Long postId
) implements Serializable {}