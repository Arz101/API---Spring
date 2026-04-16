package com.spring.api.API.comments.dtos;

import java.time.OffsetDateTime;

public record CommentResponse(
        Long id,
        String content,
        OffsetDateTime datecreated,
        String username,
        Long parentId,
        Long postId,
        Long replies
) {}
