package com.spring.api.API.models.DTOs.Posts;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostDTO (
    @NotBlank String description
) {}
