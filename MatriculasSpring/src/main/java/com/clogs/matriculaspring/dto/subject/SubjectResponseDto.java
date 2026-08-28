package com.clogs.matriculaspring.dto.subject;

public record SubjectResponseDto(
    Long id,
    String name,
    String code,
    int workload
) {
}
