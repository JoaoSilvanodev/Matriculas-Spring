package com.clogs.matriculaspring.service;

import com.clogs.matriculaspring.dto.subject.SubjectRequestDto;
import com.clogs.matriculaspring.dto.subject.SubjectResponseDto;
import com.clogs.matriculaspring.model.Subject;
import com.clogs.matriculaspring.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Camada de negócios com operações CRUD
 */

@Service
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    // criar materia retornando o dto
    public SubjectResponseDto create(SubjectRequestDto dto) {
       if (repository.existsByCode(dto.code())) {
            throw new IllegalArgumentException("Subject with code " + dto.code() + " already exists.");
        }

        Subject savedSubject = repository.save(new Subject(dto.name(), dto.code(), dto.workload()));

        return new SubjectResponseDto(
                savedSubject.getId(),
                savedSubject.getName(),
                savedSubject.getCode(),
                savedSubject.getWorkload());
    }

    // Listar todas as materias
    public List<SubjectResponseDto> findAll() {
        return repository.findAll().stream()
                .map(subject -> new SubjectResponseDto(
                        subject.getId(),
                        subject.getName(),
                        subject.getCode(),
                        subject.getWorkload()))
                .toList();
    }

    // busca por Id
    public SubjectResponseDto findById(Long id) {
        Subject subject = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + id)
                );

        return new SubjectResponseDto(subject.getId(), subject.getName(), subject.getCode(), subject.getWorkload());
    }

    // atualiza materia
    public SubjectResponseDto update(Long id, SubjectRequestDto dto) {
        Subject subject = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + id));

        subject.setName(dto.name());
        subject.setCode(dto.code());
        subject.setWorkload(dto.workload());

        Subject updatedSubject = repository.save(subject);
        return new SubjectResponseDto(
                updatedSubject.getId(),
                updatedSubject.getName(),
                updatedSubject.getCode(),
                updatedSubject.getWorkload()
        );
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Subject not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
