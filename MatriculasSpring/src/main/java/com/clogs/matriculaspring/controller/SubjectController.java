package com.clogs.matriculaspring.controller;

import com.clogs.matriculaspring.dto.subject.SubjectRequestDto;
import com.clogs.matriculaspring.dto.subject.SubjectResponseDto;
import com.clogs.matriculaspring.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SubjectResponseDto> createSubject(@RequestBody SubjectRequestDto subject) {

        SubjectResponseDto response = service.create(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> updateSubject(
            @PathVariable Long id,
            @RequestBody SubjectRequestDto subject) {


        return ResponseEntity.ok(service.update(id, subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
