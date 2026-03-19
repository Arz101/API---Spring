package com.spring.api.API.models.DTOs.Profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileDTO {

    @NotNull
    private Long user_id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @Size(min = 3, max = 20)
    private String lastname;

    private LocalDate birthday;
    private String avatar_url = null;

    @NotNull
    private Boolean privateField;
}
