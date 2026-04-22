package org.example.eshop.controller;

import jakarta.validation.Valid;
import org.example.eshop.dto.UpdateUserDto;
import org.example.eshop.dto.ViewUserDto;
import org.example.eshop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ViewUserDto getById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @GetMapping
    public Page<ViewUserDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAll(pageable);
    }

    @PutMapping("/{id}")
    public ViewUserDto update(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
