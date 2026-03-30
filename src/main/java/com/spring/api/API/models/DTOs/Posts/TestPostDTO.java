package com.spring.api.API.models.DTOs.Posts;

import com.spring.api.API.models.Hashtags;

import java.time.OffsetDateTime;
import java.util.Set;

public record TestPostDTO(
        Long id,
        Long description,
        OffsetDateTime datecreated,
        Set<String> hashtags
) {
}
