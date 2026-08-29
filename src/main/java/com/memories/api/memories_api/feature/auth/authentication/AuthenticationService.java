package com.memories.api.memories_api.feature.auth.authentication;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.memories.api.memories_api.feature.auth.authentication.dto.AuthResponse;
import com.memories.api.memories_api.feature.auth.authentication.dto.LoginRequest;
import com.memories.api.memories_api.feature.auth.authentication.dto.UserSummary;
import com.memories.api.memories_api.feature.auth.exception.AuthException;
import com.memories.api.memories_api.feature.auth.token.jwt.JwtProperties;
import com.memories.api.memories_api.feature.auth.token.jwt.JwtService;
import com.memories.api.memories_api.feature.auth.token.refresh.service.RefreshTokenService;
import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.auth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthException("Invalid email or password");
        }

        // 1. Generate Access Token
        String accessToken = jwtService.generateAccessToken(user.getEmail());

        // 2. Generate Refresh Token
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        // 3. Calculate expiration
        Instant refreshTokenExpiresAt = Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiration());

        // 4. Save refresh token
        refreshTokenService.save(user, refreshToken, refreshTokenExpiresAt);

        // 5. User response
        UserSummary userSummary = new UserSummary(
                user.getId(),
                user.getEmail(),
                user.getUsername());

        return new AuthResponse(accessToken, refreshToken, userSummary);

    }

}
