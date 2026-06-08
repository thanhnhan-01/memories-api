package com.memories.api.memories_api.feature.auth.dto.response;

import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String email,
        String username) {
}