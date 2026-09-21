package com.memories.api.memories_api.feature.memory.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.auth.user.UserRepository;
import com.memories.api.memories_api.feature.memory.dto.CreateMemoryRequest;
import com.memories.api.memories_api.feature.memory.dto.MemoryResponse;
import com.memories.api.memories_api.feature.memory.dto.UpdateMemoryRequest;
import com.memories.api.memories_api.feature.memory.service.MemoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/memories")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<MemoryResponse> create(
            @Valid @RequestBody CreateMemoryRequest request, Authentication authentication) {

        User user = getCurrentUser(authentication);

        MemoryResponse response = memoryService.create(request, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping
    public ResponseEntity<List<MemoryResponse>> getAll(Authentication authentication) {
        User user = getCurrentUser(authentication);

        return ResponseEntity.ok(memoryService.getAll(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoryResponse> getById(
            @PathVariable UUID id, Authentication authentication) {

        User user = getCurrentUser(authentication);

        return ResponseEntity.ok(memoryService.getById(id, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoryResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMemoryRequest request,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        return ResponseEntity.ok(memoryService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, Authentication authentication) {
        User user = getCurrentUser(authentication);

        memoryService.delete(id, user);

        return ResponseEntity.noContent().build();
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}