package com.example.studentsdbapp.repository.pg;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentsdbapp.entities.Student;

public interface PgRepo extends JpaRepository<Student, Integer> {
    
}
