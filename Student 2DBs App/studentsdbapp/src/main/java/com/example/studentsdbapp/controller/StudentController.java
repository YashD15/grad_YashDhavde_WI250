package com.example.studentsdbapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.studentsdbapp.entities.Student;
import com.example.studentsdbapp.repository.h2.H2Repo;
import com.example.studentsdbapp.repository.pg.PgRepo;



@Controller
public class StudentController {
    
    @Autowired
    private H2Repo h2;

    @Autowired
    private PgRepo pg;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/insert")
    public String insert(@RequestParam int rollno, String name, int standard, String school, Model model) {
        Student s = new Student(rollno, name, standard, school);
        h2.save(s);
        pg.save(s);
        model.addAttribute("message", "New Student Record added Successfully");
        return "index"; 
    }
    
}
