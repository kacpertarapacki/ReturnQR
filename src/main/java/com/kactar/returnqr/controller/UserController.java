package com.kactar.returnqr.controller;
import com.kactar.returnqr.dto.UserDto;
import com.kactar.returnqr.mapper.UserMapper;
import com.kactar.returnqr.model.Parcel;
import com.kactar.returnqr.model.User;
import com.kactar.returnqr.service.ParcelService;
import com.kactar.returnqr.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ParcelService parcelService;


    @Autowired
    public UserController(UserService userService, ParcelService parcelService) {
        this.userService = userService;
        this.parcelService = parcelService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/parcels")
    public ResponseEntity<Parcel> addParcelToUser(@Valid @RequestBody Parcel parcel, @PathVariable Long userId){
        Parcel savedParcel = parcelService.addParcelToUser(userId, parcel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParcel);
    }
}