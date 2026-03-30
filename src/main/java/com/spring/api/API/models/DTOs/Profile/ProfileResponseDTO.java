package com.spring.api.API.models.DTOs.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public record ProfileResponseDTO(
        Long profile_id,
        String name,
        String lastname,
        LocalDate birthday,
        String avatar_url,
        String bio,
        Boolean privateField
){}