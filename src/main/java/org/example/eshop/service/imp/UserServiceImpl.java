package org.example.eshop.service.imp;

import jakarta.transaction.Transactional;
import org.example.eshop.exception.NotFoundException;
import org.example.eshop.dto.*;
import org.example.eshop.mapper.UserMapper;
import org.example.eshop.model.entities.Role;
import org.example.eshop.model.entities.User;
import org.example.eshop.model.repositories.UserRepository;
import org.example.eshop.security.JwtService;
import org.example.eshop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;

    }

    @Override
    public ViewUserDto register(RegisterUserDto registerUserDto) {
        if (!Objects.equals(registerUserDto.getPassword(), registerUserDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        User user = userMapper.toUser(registerUserDto);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRole(Role.USER);

        return userMapper.toViewUserDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginUserDto dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {

            User user = userRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(user.getEmail());

            return new LoginResponseDto(
                    token,
                    user.getUserName(),
                    user.getRole().toString()
            );
        }

        throw new RuntimeException("Invalid credentials");
    }

    @Override
    @Transactional
    public ViewUserDto findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toViewUserDto)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    @Override
    @Transactional
    public Page<ViewUserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toViewUserDto);
    }

    @Override
    public ViewUserDto update(UUID id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        if (updateUserDto.getEmail() != null && !Objects.equals(updateUserDto.getEmail(), user.getEmail())) {
            if (userRepository.findByEmail(updateUserDto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email is already in use.");
            }
        }

        userMapper.updateUser(updateUserDto, user);
        return userMapper.toViewUserDto(userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }
}