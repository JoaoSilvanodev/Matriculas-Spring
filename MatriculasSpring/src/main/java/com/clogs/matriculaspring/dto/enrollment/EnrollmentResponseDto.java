package com.clogs.matriculaspring.dto.enrollment;

import java.time.LocalDateTime;

public record EnrollmentResponseDto(
        Long id,
        String userName,
        String userEmail,
        String subjectName,
        String subjectCode,
        LocalDateTime enrolledAt
) {
}
