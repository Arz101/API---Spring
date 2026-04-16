package com.spring.api.API.users.dtos;

import java.time.OffsetDateTime;

public record UsersBlocked(
        Long blockedUserId,
        String username,
        OffsetDateTime datecreated
) {}
