package com.kactar.returnqr.dto.auth;

import com.kactar.returnqr.model.UserRole;
import jakarta.validation.constraints.Email;

public record AuthResponse(
        String accesToken,
        String tokenType,
        long expiresAt,
        UserRole role
) {
}
