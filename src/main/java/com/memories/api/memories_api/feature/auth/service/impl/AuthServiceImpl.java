package com.memories.api.memories_api.feature.auth.service.impl;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.memories.api.memories_api.feature.auth.dto.request.RegisterRequest;
import com.memories.api.memories_api.feature.auth.dto.response.RegisterResponse;
import com.memories.api.memories_api.feature.auth.entity.User;
import com.memories.api.memories_api.feature.auth.exception.AuthException;
import com.memories.api.memories_api.feature.auth.repository.UserRepository;
import com.memories.api.memories_api.feature.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
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
