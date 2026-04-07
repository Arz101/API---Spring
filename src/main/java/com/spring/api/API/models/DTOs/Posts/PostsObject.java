package com.spring.api.API.models.DTOs.Posts;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
public class PostsObject {
    public Long id;
    public String description;
    public String picture;
    public String username;
    public Long likes;
    public Long comments;
    public OffsetDateTime datecreated;

    public PostsObject(@NonNull PostResponse p){
        this.id = p.id();
        this.username = p.username();
        this.description = p.description();
        this.picture = p.picture();
        this.likes = p.likes();
        this.comments = p.comments();
        this.datecreated = p.datecreated();
    }

    public PostResponse toPostResponse(){
        return new PostResponse(
                this.getId(),
                this.getDescription(),
                this.getPicture(),
                this.getUsername(),
                this.getLikes(),
                this.getComments(),
                this.getDatecreated()
        );
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || o.getClass() != getClass()) return false;
        PostsObject post = (PostsObject) o;
        return Objects.equals(id, post.id)
                && Objects.equals(username, post.username);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
