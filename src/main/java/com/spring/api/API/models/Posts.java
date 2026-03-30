package com.spring.api.API.models;


import com.spring.api.API.models.DTOs.Posts.CreatePostDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(
    name = "posts",
    indexes = {
            @Index(name = "ix_Publications_id", columnList = "id")
    }
)
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 255)
    @Column(name = "picture")
    private String picture;

    @ColumnDefault("now()")
    @Column(name = "datecreated")
    private OffsetDateTime datecreated = OffsetDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "post_hashtag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtags> hashtags = new HashSet<>();

    public Posts(){}

    public Posts(CreatePostDTO postsDTO, User user, Set<Hashtags> hashtags){
        this.description = postsDTO.description();
        this.picture = postsDTO.picture();
        this.user = user;
        this.hashtags = hashtags;
    }
}
