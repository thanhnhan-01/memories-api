package com.memories.api.memories_api.feature.auth.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.memories.api.memories_api.feature.auth.authentication.dto.AuthResponse;
import com.memories.api.memories_api.feature.auth.authentication.dto.LoginRequest;
import com.memories.api.memories_api.feature.auth.authentication.dto.UserSummary;
import com.memories.api.memories_api.feature.auth.exception.AuthException;
import com.memories.api.memories_api.feature.auth.token.JwtService;
import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.auth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user.getEmail());

        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        UserSummary userSummary = new UserSummary(
                user.getId(),
                user.getEmail(),
                user.getUsername());

        return new AuthResponse(accessToken, refreshToken, userSummary);

    }

}
