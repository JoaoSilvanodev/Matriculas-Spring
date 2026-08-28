package com.clogs.matriculaspring.service;

import com.clogs.matriculaspring.dto.auth.RegisterRequestDto;
import com.clogs.matriculaspring.dto.auth.UserResponseDto;
import com.clogs.matriculaspring.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.clogs.matriculaspring.repository.UserRepository;
import org.springframework.stereotype.Service;


// Determina as regras de negócio da autenticação
@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    // construtor da classe, injeta as dependências
    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    // Metodo de registro
    public UserResponseDto register(RegisterRequestDto request) {
        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        String hashPass = passwordEncoder.encode(request.password());

        User savedUser = repository.save(new User(request.name(), request.email(), hashPass));

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    // Metodo de login
    public String login(String email, String password) {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return tokenService.generateToken(user);
    }
}
