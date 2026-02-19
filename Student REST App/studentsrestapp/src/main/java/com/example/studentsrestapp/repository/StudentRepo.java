package com.example.studentsrestapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentsrestapp.entities.Student;

public interface StudentRepo extends JpaRepository<Student, String> {
    List<Student> findBySchool(String school);

    long countBySchool(String school);

    long countByStandard(Integer standard);

    long countByGenderAndStandard(String gender, Integer standard);

    List<Student> findByPercentageGreaterThanEqualOrderByPercentageDesc(Double percentage);

    List<Student> findByPercentageLessThanOrderByPercentageDesc(Double percentage);
   
}