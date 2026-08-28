package com.clogs.matriculaspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@Getter @Setter
public class Subject {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "code", nullable = false, unique = true)
    private String code;


    @Column(name = "workload", nullable = false)
    private int workload;


    public Subject() {}

    public Subject(String name, String code, int workload) {
        this.name = name;
        this.code = code;
        this.workload = workload;
    }

}
