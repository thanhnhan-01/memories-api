package com.memories.api.memories_api.feature.auth.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") @NotBlank String email,

        @NotBlank(message = "Password is required") @NotBlank String password) {
}
