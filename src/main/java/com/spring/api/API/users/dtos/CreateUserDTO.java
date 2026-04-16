package com.spring.api.API.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO (
    @NotBlank String username,
    @Email String email,
    @NotBlank String password,
    String status
) {
    public CreateUserDTO{
        if(status == null) status = "active";
    }
}
