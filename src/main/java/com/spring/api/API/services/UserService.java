package com.spring.api.API.services;

import com.spring.api.API.Repositories.IUserRepository;
import com.spring.api.API.models.DTOs.User.CreateUserDTO;
import com.spring.api.API.models.DTOs.User.UserResponseDTO;
import com.spring.api.API.models.User;
import com.spring.api.API.security.Exceptions.EmailException;
import com.spring.api.API.security.Exceptions.UserAlreadyExistsException;
import com.spring.api.API.security.Exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenService tokenService;

    public UserService(IUserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService,
                       TokenService tokenService){
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @Transactional
    public UserResponseDTO create(CreateUserDTO user){
        if (this.repository.existsByUsername(user.getUsername()) ||
                this.repository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExistsException("User already exists");

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        User new_user = repository.saveAndFlush(new User(user));

        String token = this.tokenService.saveAccountTokens(new_user);

        try {
            this.emailService.sendHTMLEmail(new_user.getEmail(), "Confirm your account", token);
        } catch (Exception e) {
            throw new EmailException("Failed to send confirmation email: " + e.getMessage());
        }

        return new UserResponseDTO(
                new_user.getId(),
                new_user.getUsername(),
                new_user.getEmail(),
                new_user.getStatus()
        );
    }

    public UserResponseDTO findById(Long id){
        User user = this.repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus()
        );
    }

    public UserResponseDTO findByUsername(String username){
        User user = this.repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus()
        );
    }
}
