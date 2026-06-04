package com.memories.api.memories_api.feature.auth.dto.request;

public record RegisterRequest(
        String email,
        String user,
        String password,
        String confirmPassword) {

}
