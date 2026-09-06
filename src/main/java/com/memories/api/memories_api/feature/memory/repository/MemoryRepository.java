package com.memories.api.memories_api.feature.memory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.memory.entity.Memory;

public interface MemoryRepository extends JpaRepository<Memory, UUID> {

    List<Memory> findAllByUser(User user);

    Optional<Memory> findByIdAndUser(UUID id, User user);

}
