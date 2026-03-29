package com.spring.api.API.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Entity
@Table(name = "follows")
public class Follows {

    public Follows(){}
    public Follows(User follower, User followed, String status) {
        this.follower = follower;
        this.followed = followed;
        this.status = status;

        this.id = new FollowsId(
                follower.getId(),
                followed.getId()
        );
    }

    @EmbeddedId
    private FollowsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followedId")
    @JoinColumn(name = "followed_id")
    private User followed;

    @ColumnDefault("now()")
    @Column(name = "follow_date")
    private OffsetDateTime followDate = OffsetDateTime.now();

    @Column(name = "status")
    private String status;

    public FollowsId getId() {
        return id;
    }

    public void setId(FollowsId id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public OffsetDateTime getFollowDate() {
        return followDate;
    }

    public void setFollowDate(OffsetDateTime followDate) {
        this.followDate = followDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}