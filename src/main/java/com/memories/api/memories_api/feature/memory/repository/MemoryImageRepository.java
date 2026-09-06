package com.memories.api.memories_api.feature.memory.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memories.api.memories_api.feature.memory.entity.MemoryImage;

public interface MemoryImageRepository extends JpaRepository<MemoryImage, UUID> {

    List<MemoryImage> findAllByMemoryId(UUID memoryId);

}