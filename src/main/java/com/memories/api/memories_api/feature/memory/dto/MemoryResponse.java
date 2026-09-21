package com.memories.api.memories_api.feature.memory.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record MemoryResponse(
        UUID id,
        String title,
        String content,
        LocalDate memoryDate,
        String location,
        Instant createdAt,
        Instant updatedAt) {
}
