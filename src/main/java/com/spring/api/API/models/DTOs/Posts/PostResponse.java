package com.spring.api.API.models.DTOs.Posts;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String description;
    private String picture;
    private String username;
    private Long likes;
    private Long comments;
    private OffsetDateTime datecreated;
    public PostResponse(
        Long id,
        String description,
        String picture,
        String username,
        Long likes,
        Long comments,
        OffsetDateTime datecreated
    ) {
        this.id = id;
        this.description = description;
        this.picture = picture;
        this.username = username;
        this.likes = likes;
        this.comments = comments;
        this.datecreated = datecreated;
    }
}
