package com.clogs.matriculaspring.service;

import com.clogs.matriculaspring.dto.enrollment.EnrollmentRequestDto;
import com.clogs.matriculaspring.dto.enrollment.EnrollmentResponseDto;
import com.clogs.matriculaspring.model.Enrollment;
import com.clogs.matriculaspring.model.Subject;
import com.clogs.matriculaspring.model.User;
import com.clogs.matriculaspring.repository.EnrollmentRepository;
import com.clogs.matriculaspring.repository.SubjectRepository;
import com.clogs.matriculaspring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {


    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }


    // criar matricula recebendo o userId e SubjectID
    public EnrollmentResponseDto createEnrollment(EnrollmentRequestDto request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (enrollmentRepository.findByUserIdAndSubjectId(request.userId(),
                request.subjectId()).isPresent()) {
            throw new RuntimeException("Enrollment already exists");
        }

        Enrollment saved = enrollmentRepository.save(
                new Enrollment(user, subject)
        );

        return new EnrollmentResponseDto(
                saved.getId(),
                user.getName(),
                user.getEmail(),
                subject.getName(),
                subject.getCode(),
                saved.getEnrolledAt()
        );
    }

    public List<EnrollmentResponseDto> listEnrollments() {

        return enrollmentRepository.findAll().stream()
                .map(enrollment -> {
                    User user = enrollment.getUser();
                    Subject subject = enrollment.getSubject();
                    return new EnrollmentResponseDto(
                            enrollment.getId(),
                            user.getName(),
                            user.getEmail(),
                            subject.getName(),
                            subject.getCode(),
                            enrollment.getEnrolledAt()
                    );
                })
                .collect(Collectors.toList());
    }

    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found");
        }
        enrollmentRepository.deleteById(id);
    }
}
