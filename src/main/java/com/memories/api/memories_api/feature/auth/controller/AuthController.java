package com.memories.api.memories_api.feature.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.memories.api.memories_api.feature.auth.dto.request.RegisterRequest;
import com.memories.api.memories_api.feature.auth.dto.response.RegisterResponse;
import com.memories.api.memories_api.feature.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

}
