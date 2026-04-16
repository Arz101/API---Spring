package com.spring.api.API.follows.Follows;

import com.spring.api.API.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "follows")
public class Follows {

    @EmbeddedId
    private FollowsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("follower")
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followed")
    @JoinColumn(name = "followed_id")
    private User followed;

    @ColumnDefault("now()")
    @Column(name = "follow_date")
    private OffsetDateTime followDate = OffsetDateTime.now();

    @Column(name = "status")
    private String status;

    protected Follows(){}

    public Follows(User follower, User followed, String status) {
        this.status = status;
        this.follower = follower;
        this.followed = followed;
        this.id = new FollowsId(follower.getId(), followed.getId());
    }
}