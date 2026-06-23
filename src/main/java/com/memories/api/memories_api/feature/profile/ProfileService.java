package com.memories.api.memories_api.feature.profile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.memories.api.memories_api.feature.auth.user.User;
import com.memories.api.memories_api.feature.auth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileResponse getCurrentProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        return new ProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername());

    }

}
