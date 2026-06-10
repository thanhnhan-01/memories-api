package com.memories.api.memories_api.feature.auth.registration;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.memories.api.memories_api.feature.auth.exception.AuthException;
import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.auth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AuthException("Email already exists");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new AuthException("Username already exists");
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new AuthException("Password confirmation does not match");
        }

        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(Objects.requireNonNull(user));

        return new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername());
    }

}
