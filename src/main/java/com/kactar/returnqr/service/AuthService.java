package com.kactar.returnqr.service;

import com.kactar.returnqr.dto.UserDto;
import com.kactar.returnqr.dto.auth.AuthResponse;
import com.kactar.returnqr.dto.auth.LoginRequest;
import com.kactar.returnqr.dto.auth.RegisterRequest;
import com.kactar.returnqr.mapper.UserMapper;
import com.kactar.returnqr.model.User;
import com.kactar.returnqr.model.UserRole;
import com.kactar.returnqr.repository.UserRepository;
import com.kactar.returnqr.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final long jwtExpiration;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${app.jwt.expiration}") long jwtExpiration, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtExpiration = jwtExpiration;
        this.jwtService = jwtService;
    }

    public UserDto register(RegisterRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (request.password().length() < 6){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password too short (min 6 characters)");
        }
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.name(), request.email(), encodedPassword, UserRole.USER);
        return UserMapper.toDto(userRepository.save(user));
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        long expiresAt = System.currentTimeMillis() + jwtExpiration;
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, "Bearer", expiresAt, user.getUserRole());
    }



}
