package com.spring.api.API.comments.dtos;

import jakarta.validation.constraints.NotBlank;

public record CommentReplyCreate(
        @NotBlank String content
) {}
