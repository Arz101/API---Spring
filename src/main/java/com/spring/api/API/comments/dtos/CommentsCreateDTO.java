package com.spring.api.API.comments.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentsCreateDTO (
    @NotNull Long postId,
    @NotBlank String content
) {}
