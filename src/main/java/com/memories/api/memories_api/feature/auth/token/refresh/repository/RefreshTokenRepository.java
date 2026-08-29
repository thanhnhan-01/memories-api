package com.memories.api.memories_api.feature.auth.token.refresh.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memories.api.memories_api.feature.auth.token.refresh.entity.RefreshToken;
import com.memories.api.memories_api.feature.auth.user.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUser(User user);
}
