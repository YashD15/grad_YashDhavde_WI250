package com.example.studentsrestapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="students")
public class Student {

    @Id
    private String regno;
    private String rollno;
    private String name;
    private Integer standard;
    private String school;
    private String gender;
    private Double percentage;

    public Student() {
    }

    public Student(String regno, String rollno, String name, Integer standard, String school, String gender,
            Double percentage) {
        this.regno = regno;
        this.rollno = rollno;
        this.name = name;
        this.standard = standard;
        this.school = school;
        this.gender = gender;
        this.percentage = percentage;
    }

    public String getRegNo() {
        return regno;
    }

    public void setRegNo(String regno) {
        this.regno = regno;
    }

    public String getRollNo() {
        return rollno;
    }

    public void setRollNo(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}