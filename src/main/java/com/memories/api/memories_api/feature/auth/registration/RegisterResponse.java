package com.memories.api.memories_api.feature.auth.registration;

import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String email,
        String username) {
}