package com.spring.api.API.models.DTOs.Posts;

import java.util.Set;

public record PostResponseWithHashtags (
    PostResponse post,
    Set<String> hashtags
) {}
