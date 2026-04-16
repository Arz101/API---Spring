package com.spring.api.API.posts.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostDTO (
    @NotBlank String description
) {}
