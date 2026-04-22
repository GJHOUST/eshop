package org.example.eshop.controller;

import jakarta.validation.Valid;
import org.example.eshop.dto.LoginResponseDto;
import org.example.eshop.dto.LoginUserDto;
import org.example.eshop.dto.RegisterUserDto;
import org.example.eshop.dto.ViewUserDto;
import org.example.eshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewUserDto register(@Valid @RequestBody RegisterUserDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginUserDto dto)  {
        return userService.login(dto);
    }
}
