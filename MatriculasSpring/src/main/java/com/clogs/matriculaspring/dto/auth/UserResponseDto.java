package com.clogs.matriculaspring.dto.auth;

public record UserResponseDto(
        Long id,
        String name,
        String email
) {

}
