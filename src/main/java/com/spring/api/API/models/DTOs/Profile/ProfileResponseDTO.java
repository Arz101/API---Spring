package com.spring.api.API.models.DTOs.Profile;

import com.spring.api.API.security.Exceptions.UserNotFoundException;
import com.spring.api.API.services.StorageService;
import org.springframework.beans.factory.annotation.Value;

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