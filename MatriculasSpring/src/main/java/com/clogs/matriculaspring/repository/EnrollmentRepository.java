package com.clogs.matriculaspring.repository;

import com.clogs.matriculaspring.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByUserIdAndSubjectId(Long userId, Long subjectId);
}
