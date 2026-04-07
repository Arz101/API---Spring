package com.spring.api.API.models.DTOs.User;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UserNode {
    public Long userId;
    public String username;

    public UserNode(Long userId, String username){
        this.userId = userId;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNode userNode = (UserNode) o;
        return Objects.equals(userId, userNode.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}