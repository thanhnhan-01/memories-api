package com.memories.api.memories_api.feature.auth.authentication.dto;

public record AuthResponse(
                String accessToken,
                String refreshToken,
                UserSummary user) {
}
