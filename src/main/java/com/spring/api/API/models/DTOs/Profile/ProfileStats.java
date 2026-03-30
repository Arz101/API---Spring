package com.spring.api.API.models.DTOs.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ProfileStats(
        Long posts,
        Long followers,
        Long followeds
) {}

