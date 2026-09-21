package com.memories.api.memories_api.feature.memory.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

public record UpdateMemoryRequest(
        @Size(max = 255, message = "Title must not exceed 255 characters") String title,

        @Size(max = 10000, message = "Content must not exceed 10000 characters") String content,

        LocalDate memoryDate,

        @Size(max = 255, message = "Location must not exceed 255 characters") String location) {
}