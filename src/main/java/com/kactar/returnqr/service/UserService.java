package com.kactar.returnqr.service;

import com.kactar.returnqr.dto.UserDto;
import com.kactar.returnqr.model.User;
import com.kactar.returnqr.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().
                stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    public void deleteUser(Long id){
        if (!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }
}
