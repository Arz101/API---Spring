package com.spring.api.API.models.DTOs.Posts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    @NotBlank
    private String description;
    @NotNull
    private Long profile_id;
    private String picture = null;
}
