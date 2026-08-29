package com.memories.api.memories_api.feature.auth.token.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories.api.memories_api.feature.auth.token.refresh.dto.LogoutResponse;
import com.memories.api.memories_api.feature.auth.token.refresh.dto.RefreshTokenRequest;
import com.memories.api.memories_api.feature.auth.token.refresh.dto.RefreshTokenResponse;
import com.memories.api.memories_api.feature.auth.token.refresh.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public RefreshTokenResponse refresh(@RequestBody RefreshTokenRequest request) {
        String newAccessToken = refreshTokenService.refreshAccessToken(request.refreshToken());

        return new RefreshTokenResponse(newAccessToken);
    }

    @PostMapping("/logout")
    public LogoutResponse logout(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.revoke(request.refreshToken());

        return new LogoutResponse("Logout Successfully");
    }

}
