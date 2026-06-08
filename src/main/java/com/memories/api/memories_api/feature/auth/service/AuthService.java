package com.memories.api.memories_api.feature.auth.service;

import com.memories.api.memories_api.feature.auth.dto.request.RegisterRequest;
import com.memories.api.memories_api.feature.auth.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

}