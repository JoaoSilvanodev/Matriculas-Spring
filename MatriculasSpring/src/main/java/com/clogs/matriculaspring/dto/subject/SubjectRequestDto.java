package com.clogs.matriculaspring.dto.subject;

public record SubjectRequestDto(
    String name,
    String code,
    int workload
) {

}