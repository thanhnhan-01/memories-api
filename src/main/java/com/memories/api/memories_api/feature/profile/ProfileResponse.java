package com.memories.api.memories_api.feature.profile;

import java.util.UUID;

public record ProfileResponse(UUID id, String email, String username) {
}
