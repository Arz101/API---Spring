package com.spring.api.API.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FollowsId implements Serializable {

    private Long followerId;
    private Long followedId;

    public FollowsId() {}

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }

    public FollowsId(Long followerId, Long followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FollowsId followsId = (FollowsId) o;
        return Objects.equals(followerId, followsId.followerId) && Objects.equals(followedId, followsId.followedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followedId);
    }
}
