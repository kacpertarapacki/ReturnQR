package com.kactar.returnqr.mapper;

import com.kactar.returnqr.dto.UserDto;
import com.kactar.returnqr.dto.auth.RegisterRequest;
import com.kactar.returnqr.model.User;

public class UserMapper {
    public static User toEntity(RegisterRequest request){
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        return user;
    }

    public static UserDto toDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getUserRole());
    }
}
