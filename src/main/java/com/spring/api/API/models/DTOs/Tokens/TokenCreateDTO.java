package com.spring.api.API.models.DTOs.Tokens;

import com.spring.api.API.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenCreateDTO {
    String token_hash;
    User assigned_to;
    Short token_type;
    Boolean revoked;
}