package com.memories.api.memories_api.feature.auth.authentication;

public record AuthResponse(
                String accessToken,
                String refreshToken) {

}
