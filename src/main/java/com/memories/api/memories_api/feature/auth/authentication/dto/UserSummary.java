package com.memories.api.memories_api.feature.auth.authentication.dto;

import java.util.UUID;

public record UserSummary(
        UUID id,
        String email,
        String username) {

}
