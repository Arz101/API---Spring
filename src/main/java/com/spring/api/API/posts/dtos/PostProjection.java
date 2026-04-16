package com.spring.api.API.posts.dtos;

import java.time.Instant;

public interface PostProjection {
    Long getId();
    String getDescription();
    String getPicture();
    String getUsername();
    Long getLikes();
    Long getComments();
    Instant getDatecreated();
}
