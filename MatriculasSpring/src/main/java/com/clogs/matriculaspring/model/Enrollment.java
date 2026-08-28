package com.clogs.matriculaspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter @Setter
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private LocalDateTime enrolledAt;


    public Enrollment() {
    }

    public Enrollment(User user, Subject subject) {
        this.user = user;
        this.subject = subject;
    }

    @PrePersist
    protected void onEnroll() {
        this.enrolledAt = LocalDateTime.now();
    }
}