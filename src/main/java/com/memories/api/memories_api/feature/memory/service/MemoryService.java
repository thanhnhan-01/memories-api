package com.memories.api.memories_api.feature.memory.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.memory.dto.CreateMemoryRequest;
import com.memories.api.memories_api.feature.memory.dto.MemoryResponse;
import com.memories.api.memories_api.feature.memory.dto.UpdateMemoryRequest;
import com.memories.api.memories_api.feature.memory.entity.Memory;
import com.memories.api.memories_api.feature.memory.repository.MemoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoryService {

    private final MemoryRepository memoryRepository;

    public MemoryResponse create(
            CreateMemoryRequest request, User user

    ) {
        Memory memory = Memory.builder()
                .title(request.title())
                .content(request.content())
                .memoryDate(request.memoryDate())
                .location(request.location())
                .user(user)
                .build();

        Memory savedMemory = memoryRepository.save(memory);

        return toResponse(savedMemory);
    }

    @Transactional(readOnly = true)
    public MemoryResponse getById(UUID id, User user) {
        Memory memory = memoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Memory not found"));

        return toResponse(memory);
    }

    @Transactional(readOnly = true)
    public List<MemoryResponse> getAll(User user) {
        return memoryRepository.findAllByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MemoryResponse update(UUID id, UpdateMemoryRequest request, User user) {
        Memory memory = memoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Memory not found"));

        if (request.title() != null) {
            memory.setTitle(request.title());
        }

        if (request.content() != null) {
            memory.setContent(request.content());
        }

        if (request.memoryDate() != null) {
            memory.setMemoryDate(request.memoryDate());
        }

        if (request.location() != null) {
            memory.setLocation(request.location());
        }

        return toResponse(memory);
    }

    public void delete(UUID id, User user) {
        Memory memory = memoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Memory not found"));

        memoryRepository.delete(memory);
    }

    private MemoryResponse toResponse(Memory memory) {
        return new MemoryResponse(
                memory.getId(),
                memory.getTitle(),
                memory.getContent(),
                memory.getMemoryDate(),
                memory.getLocation(),
                memory.getCreatedAt(),
                memory.getUpdatedAt());
    }
}