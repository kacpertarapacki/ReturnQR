package com.kactar.returnqr.controller;

import com.kactar.returnqr.dto.UserDto;
import com.kactar.returnqr.dto.auth.AuthResponse;
import com.kactar.returnqr.dto.auth.LoginRequest;
import com.kactar.returnqr.dto.auth.RegisterRequest;
import com.kactar.returnqr.security.CustomUserDetails;
import com.kactar.returnqr.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request){
        UserDto userDto = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok("Zalogowany jako: " + userDetails.getUsername() + " z rolą: " + userDetails.getAuthorities());
    }


}
