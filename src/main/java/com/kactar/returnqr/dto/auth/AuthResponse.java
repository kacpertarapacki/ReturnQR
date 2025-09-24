package com.kactar.returnqr.dto.auth;

import com.kactar.returnqr.model.UserRole;


public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresAt,
        UserRole role
) {
}
