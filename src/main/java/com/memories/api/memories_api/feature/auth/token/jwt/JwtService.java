package com.memories.api.memories_api.feature.auth.token.jwt;

import javax.crypto.SecretKey;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

        private final JwtProperties jwtProperties;

        private SecretKey getSigningKey() {
                return Keys.hmacShaKeyFor(
                                jwtProperties.getSecret().getBytes());
        }

        public String generateAccessToken(String email) {
                return Jwts.builder()
                                .subject(email)
                                .issuedAt(new Date())
                                .expiration(new Date(
                                                System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                                .signWith(getSigningKey())
                                .compact();
        }

        public String generateRefreshToken(String email) {
                return Jwts.builder()
                                .subject(email)
                                .issuedAt(new Date())
                                .expiration(new Date(
                                                System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
                                .signWith(getSigningKey())
                                .claim("type", "refresh")
                                .compact();
        }

        public String extractUsername(String token) {

                Claims claims = Jwts.parser()
                                .verifyWith(getSigningKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

                return claims.getSubject();
        }

        public boolean isTokenValid(String token, String email) {

                String username = extractUsername(token);

                return username.equals(email)
                                && !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {

                Claims claims = Jwts.parser()
                                .verifyWith(getSigningKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

                return claims.getExpiration()
                                .before(new Date());
        }

}
