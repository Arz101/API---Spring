package com.spring.api.API.models.DTOs.Comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UpdateCommentDTO (
    @NotNull Long id,
    @NotBlank String content
) {}
