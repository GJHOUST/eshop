package org.example.eshop.service;

import org.example.eshop.dto.*;
import org.example.eshop.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    ViewUserDto register(RegisterUserDto registerUserDto);
    LoginResponseDto login(LoginUserDto loginUserDto);
    ViewUserDto findById(UUID id);
    Page<ViewUserDto> getAll(Pageable pageable);
    ViewUserDto update(UUID id, UpdateUserDto updateUserDto);
    void delete(UUID id);
    User getUserByEmail(String email);
}
