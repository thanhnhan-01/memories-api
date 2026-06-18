package com.memories.api.memories_api.feature.auth.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
                @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,

                @NotBlank(message = "Username is required") @Size(min = 3, max = 50) String username,

                @NotBlank(message = "Password is required") @Size(min = 8, max = 255) String password,

                @NotBlank(message = "Confirm password is required") String confirmPassword) {
}
