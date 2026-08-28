package com.clogs.matriculaspring.controller;

import com.clogs.matriculaspring.dto.auth.LoginRequestDto;
import com.clogs.matriculaspring.dto.auth.RegisterRequestDto;
import com.clogs.matriculaspring.dto.auth.UserResponseDto;
import com.clogs.matriculaspring.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;


    public AuthController(AuthService service) {
        this.service = service;
    }

    // Metodo POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @RequestBody @Valid RegisterRequestDto dto
            ) {
        UserResponseDto response = service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Metodo POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid LoginRequestDto dto
            ) {
            String token = service.login(dto.email(), dto.password());
            return ResponseEntity.ok(token);
    }


}
