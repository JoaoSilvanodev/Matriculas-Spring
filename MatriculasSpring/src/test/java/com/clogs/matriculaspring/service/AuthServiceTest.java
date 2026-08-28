package com.clogs.matriculaspring.service;

import com.clogs.matriculaspring.dto.auth.RegisterRequestDto;
import com.clogs.matriculaspring.dto.auth.UserResponseDto;
import com.clogs.matriculaspring.model.User;
import com.clogs.matriculaspring.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Should authenticate user with valid credentials")
    void shouldAuthenticateUserWithValidCredentials() {
        RegisterRequestDto requestDto =
                new RegisterRequestDto(
                        "testuser",
                        "test@mail.com",
                        "encodedPassword"
                );
        User savedUser =
                new User(
                        "testuser",
                        "test@mail.com",
                        "encodedPassword"
                );
        savedUser.setId(1L);

        when(userRepository.existsByEmail("test@mail.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);


        UserResponseDto responseDto = authService.register(requestDto);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.id());
        assertEquals("testuser", responseDto.name());
        assertEquals("test@mail.com", responseDto.email());

        verify(userRepository).save(any(User.class));

    }

    @Test
    @DisplayName("Should not register user with existing email")
    void shouldNotRegisterUserWithExistingEmail() {
        RegisterRequestDto requestDto =
                new RegisterRequestDto(
                        "testuser",
                        "test@mail.com",
                        "encodedPassword"
                );

        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.register(requestDto));

        assertEquals("Email already in use" , exception.getMessage());

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should login user with valid credentials and return token")
    void shouldLoginWithSucess() {
        User user = new User("testuser", "test@mail.com", "encodedPassword");

        user.setId(1L);

        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(user));

        when(passwordEncoder.matches("senha123", "encodedPassword"))
                .thenReturn(true);

        when(tokenService.generateToken(user)).thenReturn("mocked-jwt-token");

        String token = authService.login("test@mail.com","senha123");

        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);
        verify(tokenService, times(1)).generateToken(user);
        
    }

    @Test
    @DisplayName("Deve lançar exceção no login quando a senha estiver incorreta")
    void shouldThrowExceptionWhenPasswordIsIncorrectOnLogin() {

        User user = new User("testuser", "test@mail.com", "encodedPassword");
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senhaErrada", "encodedPassword")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login("test@mail.com", "senhaErrada")
        );
        assertEquals("Invalid email or password", exception.getMessage());
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Deve lançar exceção no login quando o e-mail não for encontrado")
    void shouldThrowExceptionWhenUserNotFoundOnLogin() {

        when(userRepository.findByEmail("inexistente@email.com")).thenReturn(Optional.empty());
        
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login("inexistente@email.com", "senha123")
        );
        assertEquals("Invalid email or password", exception.getMessage());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(tokenService, never()).generateToken(any());
    }
}
