package com.spring.api.API.chat;

import com.spring.api.API.users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "participations")
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
