package com.spring.api.API.comments.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCommentDTO (
    @NotNull Long id,
    @NotBlank String content
) {}
