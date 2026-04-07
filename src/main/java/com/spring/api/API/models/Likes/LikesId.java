package com.spring.api.API.models.Likes;

import com.spring.api.API.models.Follows.FollowsId;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LikesId implements Serializable {
    private Long userId;
    private Long postId;

    protected LikesId(){}

    public LikesId(Long userId, Long postId){
        this.postId = postId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LikesId followsId = (LikesId) o;
        return Objects.equals(userId, followsId.userId) && Objects.equals(postId, followsId.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }
}
