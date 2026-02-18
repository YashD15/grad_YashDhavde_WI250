package com.example.studentsdbapp.repository.h2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentsdbapp.entities.Student;

public interface H2Repo extends JpaRepository<Student, Integer>{
    
}
