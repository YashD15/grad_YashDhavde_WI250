package com.example.studentsrestapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentsrestapp.entities.Student;
import com.example.studentsrestapp.repository.StudentRepo;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepo repo;

    // ALL ENDPOINTS:
    // GET /students
    // GET /students/{regNo}
    // POST /students
    // PUT /students/{regNo}
    // DELETE /students/{regNo}
    // GET /students/school?name=KV
    // GET /students/school/count?name=DPS
    // GET /students/school/standard/count?class=5
    // GET /students/strength?gender=MALE&standard=5
    // PATCH /students/{regNo}
    // GET /students/result?pass=true

    @GetMapping
    public List<Student> getAll() {
        return repo.findAll();
    }
    
    @GetMapping("/{regNo}")
    public Optional<Student> getByRegNo(@PathVariable String regNo) {
        return repo.findById(regNo);
    }
    
    @PostMapping
    public Student create(@RequestBody Student student) {
        return repo.save(student);
    }
    
    @PutMapping("/{regNo}")
    public Student update(@PathVariable String regNo, @RequestBody Student student) {
        student.setRegNo(regNo);
        return repo.save(student);
    }

    @DeleteMapping("/{regNo}")
    public void delete(@PathVariable String regNo) {
        repo.deleteById(regNo);
    }
    
    @GetMapping("/school")
    public List<Student> getBySchool(@RequestParam String name) {
        return repo.findBySchool(name);
    }
    
    @GetMapping("/school/count")
    public long countBySchool(@RequestParam String name) {
        return repo.countBySchool(name);
    }

    @GetMapping("/school/standard/count")
    public String countByStandard(@RequestParam Integer standard) {
        return "Students studying in standard "+ standard+" are " + repo.countByStandard(standard);
    }

    @GetMapping("/strength")
    public String countByGenderAndStandard(@RequestParam String gender, @RequestParam Integer standard) {
        return "Students studying in standard "+standard+" and with gender "+gender+" are " + repo.countByGenderAndStandard(gender, standard);
    }

    @PatchMapping("{regNo}")
    public String patchStudent(@PathVariable String regNo, @RequestBody Student s){
        Optional<Student> oldS = repo.findById(regNo);
        if(!oldS.isPresent())
            return "No student with this RegNo found";
        Student s1 = oldS.get();
        if(s.getRegNo() != null) s1.setRegNo(s.getRegNo());
        if(s.getRollNo() != null) s1.setRollNo(s.getRollNo());
        if(s.getName() != null) s1.setName(s.getName());
        if(s.getStandard() != null) s1.setStandard(s.getStandard());
        if(s.getSchool() != null) s1.setSchool(s.getSchool());
        if(s.getGender() != null) s1.setGender(s.getGender());
        if(s.getPercentage() != null) s1.setPercentage(s.getPercentage());
        repo.save(s1);
        return "Successfully Patched";
    }

    @GetMapping("/result")
    public List<Student> getResult(@RequestParam boolean pass) {
        if(pass)
            return repo.findByPercentageGreaterThanEqualOrderByPercentageDesc(40.0);
        else
            return repo.findByPercentageLessThanOrderByPercentageDesc(40.0);
    }

}