package com.clogs.matriculaspring.config;

import com.clogs.matriculaspring.dto.error.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto>
    handlerIllegalArgument(IllegalArgumentException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                400,
                "Bad Request",
                e.getMessage(),
                java.time.LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handlerRuntimeException(RuntimeException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                500,
                "Internal Server Error",
                e.getMessage(),
                java.time.LocalDateTime.now()
        );
        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class )
    public ResponseEntity<ErrorResponseDto> handlerValidation(
            MethodArgumentNotValidException e
    ) {
        String errorMessage = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                400,
                "Bad Request",
                errorMessage,
                java.time.LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
