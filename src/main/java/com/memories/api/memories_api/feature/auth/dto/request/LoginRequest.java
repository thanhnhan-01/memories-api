package com.memories.api.memories_api.feature.auth.dto.request;

public record LoginRequest(
        String email,
        String password) {

}
