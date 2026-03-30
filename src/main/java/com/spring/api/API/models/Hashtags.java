package com.spring.api.API.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hashtags")
public class Hashtags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "post_count", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long posts_count = 0L;

    @ColumnDefault("now()")
    @Column(name = "datecreated")
    private OffsetDateTime datecreated = OffsetDateTime.now();

    @ManyToMany(mappedBy = "hashtags", fetch = FetchType.LAZY)
    private Set<Posts> posts;

    protected Hashtags() {}
    public Hashtags(String name) {
        this.name = name;
    }
}
