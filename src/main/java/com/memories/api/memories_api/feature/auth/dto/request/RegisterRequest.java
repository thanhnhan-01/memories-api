package com.memories.api.memories_api.feature.auth.dto.request;

public record RegisterRequest(
        String email,
        String username,
        String password,
        String confirmPassword) {

}
