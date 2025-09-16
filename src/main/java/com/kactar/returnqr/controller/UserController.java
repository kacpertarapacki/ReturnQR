package com.kactar.returnqr.controller;
import com.kactar.returnqr.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getUsers(){
        return users;
    }

    @PostMapping
    public String createUser(@RequestBody User user){
        users.add(user);
        return "Dodano użytkownika: " + user.getName();
    }

    @GetMapping("/{index}")
    public User getUserByIndex(@PathVariable int index){
        return users.get(index);
    }

}
