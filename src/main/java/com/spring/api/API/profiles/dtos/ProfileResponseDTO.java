package com.spring.api.API.profiles.dtos;

import java.time.LocalDate;

public record ProfileResponseDTO(
        Long profileId,
        String name,
        String lastname,
        LocalDate birthday,
        String avatarUrl,
        String bio,
        Boolean privateField
){
    public ProfileResponseDTO{
        if(avatarUrl != null) {
            avatarUrl = "http://localhost:8080/uploads/" + avatarUrl;
        }
    }
}