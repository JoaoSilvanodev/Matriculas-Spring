package com.clogs.matriculaspring.dto.auth;

public record LoginRequestDto(
        String email,
        String password
) {}
