package com.memories.api.memories_api.feature.memory.dto;

import java.util.UUID;

public record MemoryFileResponse(
        UUID id,
        String fileName,
        String filePath,
        String contentSize,
        long fileSize) {
}
