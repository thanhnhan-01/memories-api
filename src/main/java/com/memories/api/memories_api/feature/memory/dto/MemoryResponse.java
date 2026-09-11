package com.memories.api.memories_api.feature.memory.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record MemoryResponse(
                UUID id,
                String title,
                String content,
                LocalDateTime memoryDate,
                String location,
                Instant createdAt,
                Instant updatedAt) {
}
