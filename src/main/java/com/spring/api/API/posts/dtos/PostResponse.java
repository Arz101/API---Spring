package com.spring.api.API.posts.dtos;

import java.util.Set;

public record PostResponse(
    PostData post,
    Set<String> hashtags
) {}
