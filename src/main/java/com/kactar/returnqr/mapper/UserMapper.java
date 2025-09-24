package com.kactar.returnqr.mapper;

import com.kactar.returnqr.dto.UserDto;
import com.kactar.returnqr.model.User;

public class UserMapper {
    public static UserDto toDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getUserRole());
    }
}
