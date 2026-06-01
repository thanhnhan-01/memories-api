package com.memories.api.memories_api.core.security.jwt;

import org.springframework.stereotype.Service;

import com.memories.api.memories_api.core.config.JwtProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;

    public void test() {
        System.out.println(jwtProperties.getSecret());
    }
}
