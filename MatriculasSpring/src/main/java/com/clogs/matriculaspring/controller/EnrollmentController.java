package com.clogs.matriculaspring.controller;

import com.clogs.matriculaspring.dto.enrollment.EnrollmentRequestDto;
import com.clogs.matriculaspring.dto.enrollment.EnrollmentResponseDto;
import com.clogs.matriculaspring.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;


    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }


    @PostMapping("/create")
    public ResponseEntity<EnrollmentResponseDto> createEnrollment(
            @RequestBody EnrollmentRequestDto request)
    {
        EnrollmentResponseDto response = service.createEnrollment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> listEnrollments() {
        return ResponseEntity.ok(service.listEnrollments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        service.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
