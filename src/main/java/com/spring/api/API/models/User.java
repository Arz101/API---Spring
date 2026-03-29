package com.spring.api.API.models;


import com.spring.api.API.models.DTOs.User.CreateUserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_email", columnNames = "email"),
                @UniqueConstraint(name = "UQ_username", columnNames = "username")
        },
        indexes = {
                @Index(name = "ix_user_email", columnList = "email"),
                @Index(name = "ix_Users_id", columnList = "id"),
                @Index(name = "ix_users_username", columnList = "username")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "\"dateCreated\"", nullable = false)
    private OffsetDateTime dateCreated;

    @Column(name = "status", nullable = false, length = 10)
    private String status = "pending";

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follows> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY)
    private List<Follows> followeds = new ArrayList<>();

    protected User() {}

    public User(CreateUserDTO user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();
    }
}
