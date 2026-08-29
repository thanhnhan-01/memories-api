package com.memories.api.memories_api.feature.auth.token.refresh.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.memories.api.memories_api.feature.auth.exception.AuthException;
import com.memories.api.memories_api.feature.auth.token.jwt.JwtService;
import com.memories.api.memories_api.feature.auth.token.refresh.entity.RefreshToken;
import com.memories.api.memories_api.feature.auth.token.refresh.repository.RefreshTokenRepository;
import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.auth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshToken save(User user, String token, Instant expiresAt) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(expiresAt);
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);

    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow();
    }

    public boolean isValid(RefreshToken refreshToken) {
        return !refreshToken.isRevoked() && refreshToken.getExpiresAt().isAfter(Instant.now());
    }

    public String refreshAccessToken(String refreshTokenValue) {

        RefreshToken storedToken = findByToken(refreshTokenValue);

        if (storedToken.isRevoked()) {
            throw new AuthException("Refresh token revoked");
        }

        if (storedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new AuthException("Refresh token expired");
        }

        return jwtService.generateAccessToken(
                storedToken.getUser().getEmail());
    }

    public void revoke(String token) {
        RefreshToken storedToken = findByToken(token);

        storedToken.setRevoked(true);

        refreshTokenRepository.save(storedToken);
    }

    public void revokeAllByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthException("User not found"));

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUser(user);

        refreshTokens.forEach(refreshToken -> refreshToken.setRevoked(true));

        refreshTokenRepository.saveAll(refreshTokens);
    }

}
