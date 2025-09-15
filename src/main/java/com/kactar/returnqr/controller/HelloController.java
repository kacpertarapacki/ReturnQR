package com.kactar.returnqr.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/") // obsługa root "/"
    public String home() {
        return "Welcome to ReturnQR!";
    }
    @GetMapping("/check")
    public String check(@RequestParam @NotBlank String name) {
        return "Hello " + name;
    }

}
