package com.spring.api.API.models.DTOs.Comments;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record CommentsResponse (
    Long id,
    Long post_id,
    String username,
    String content,
    OffsetDateTime dateCreated
) {}
