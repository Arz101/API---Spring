package com.spring.api.API.models.DTOs.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileStats {
    Long posts_count;
    Long followers_count;
    Long followeds_count;
}
