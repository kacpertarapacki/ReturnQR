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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ParcelService parcelService;


    public UserController(UserService userService, ParcelService parcelService) {
        this.userService = userService;
        this.parcelService = parcelService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/parcels")
    public ResponseEntity<Parcel> addParcelToUser(@Valid @RequestBody Parcel parcel, @PathVariable Long userId){
        Parcel savedParcel = parcelService.addParcelToUser(userId, parcel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParcel);
    }
}