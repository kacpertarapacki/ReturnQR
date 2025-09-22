package com.kactar.returnqr.service;

import com.kactar.returnqr.dto.UserDto;
import com.kactar.returnqr.model.User;
import com.kactar.returnqr.model.UserRole;
import com.kactar.returnqr.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public User createNewUser(User user){
//        if (userRepository.existsByEmail(user.getEmail())) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
//        if (user.getPassword().length() < 6){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password too short (min 6 symbols");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        if (user.getUserRole() == null){
//            user.setUserRole(UserRole.USER);
//        }
//        return userRepository.save(user);
//    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
    }

//    public List<UserDto> getAllUsers(){
//        return userRepository.findAll().
//                stream()
//                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
//                .toList();
//    }

    public void deleteUser(Long id){
        if (!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }
}
