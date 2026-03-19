package com.spring.api.API.models.DTOs.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private Long profile_id;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private String avatar_url;
    private Boolean privateField;
}
