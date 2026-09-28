package com.memories.api.memories_api.feature.memory.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path storageDirectory = Paths.get("uploads");

    public FileStorageService() {
        try {
            Files.createDirectories(storageDirectory.resolve("images"));
            Files.createDirectories(storageDirectory.resolve("files"));
        } catch (IOException ex) {
            throw new RuntimeException("Could not create storage directory ", ex);
        }
    }

    public String storeImage(MultipartFile file) {
        return store(file, "images");
    }

    public String storeFile(MultipartFile file) {
        return store(file, "files");
    }

    public String store(MultipartFile file, String directory) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String originalFileName = file.getOriginalFilename();
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String storedFileName = UUID.randomUUID() + extension;
        Path targetDirectory = storageDirectory.resolve(directory);
        Path targetPath = targetDirectory.resolve(storedFileName);

        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file ", ex);
        }
    }

    public void delete(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file ", ex);
        }
    }
}
