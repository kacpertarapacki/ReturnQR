package com.kactar.returnqr.dto;

import com.kactar.returnqr.model.UserRole;

public record UserDto(Long id,
                      String name,
                      String email,
                      UserRole userRole) {
}
