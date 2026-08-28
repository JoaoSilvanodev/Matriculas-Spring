package com.clogs.matriculaspring.repository;

import com.clogs.matriculaspring.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsByCode(String code);

}
