package com.memories.api.memories_api.feature.auth.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken) {

}
