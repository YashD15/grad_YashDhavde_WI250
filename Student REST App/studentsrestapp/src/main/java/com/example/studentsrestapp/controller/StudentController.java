package com.example.studentsrestapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = repo.findAll();
        if (students.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/{regNo}")
    public ResponseEntity<Student> getByRegNo(@PathVariable String regNo) {
        return repo.findById(regNo).map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(204).build());
    }
    
    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        if(repo.existsById(student.getRegNo()))
            return ResponseEntity.status(409).build();
        Student newStud = repo.save(student);
        return ResponseEntity.status(201).body(newStud);
    }
    
    @PutMapping("/{regNo}")
    public ResponseEntity<Student> update(@PathVariable String regNo, @RequestBody Student student) {
        if(repo.existsById(regNo)) {
            student.setRegNo(regNo);
            repo.save(student);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{regNo}")
    public ResponseEntity<String> delete(@PathVariable String regNo) {
        if(repo.existsById(regNo)) {
            repo.deleteById(regNo);
            return ResponseEntity.ok().body("Student record Deleted");
        }
        return ResponseEntity.status(204).body("Student Record not found");
    }
    
    @GetMapping("/school")
    public ResponseEntity<List<Student>> getBySchool(@RequestParam String name) {
        List<Student> res = repo.findBySchool(name);
        if(res.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.ok(res);
        }
    }
    
    @GetMapping("/school/count")
    public ResponseEntity<Long> countBySchool(@RequestParam String name) {
        long count = repo.countBySchool(name);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/school/standard/count")
    public ResponseEntity<String> countByStandard(@RequestParam Integer standard) {
        return ResponseEntity.ok().body("Students studying in standard "+ standard+" are " + repo.countByStandard(standard));
    }

    @GetMapping("/strength")
    public ResponseEntity<String> countByGenderAndStandard(@RequestParam String gender, @RequestParam Integer standard) {
        return ResponseEntity.ok().body("Students studying in standard "+standard+" and with gender "+gender+" are " + repo.countByGenderAndStandard(gender, standard));
    }

    @PatchMapping("{regNo}")
    public ResponseEntity<String> patchStudent(@PathVariable String regNo, @RequestBody Student s){
        Optional<Student> oldS = repo.findById(regNo);
        if(!oldS.isPresent())
            return ResponseEntity.status(204).body("No student with this RegNo found");
        Student s1 = oldS.get();
        if(s.getRegNo() != null) s1.setRegNo(s.getRegNo());
        if(s.getRollNo() != null) s1.setRollNo(s.getRollNo());
        if(s.getName() != null) s1.setName(s.getName());
        if(s.getStandard() != null) s1.setStandard(s.getStandard());
        if(s.getSchool() != null) s1.setSchool(s.getSchool());
        if(s.getGender() != null) s1.setGender(s.getGender());
        if(s.getPercentage() != null) s1.setPercentage(s.getPercentage());
        repo.save(s1);
        return ResponseEntity.ok().body("Successfully Patched");
    }

    @GetMapping("/result")
    public ResponseEntity<List<Student>> getResult(@RequestParam boolean pass) {
        List<Student> result;
        if (pass) {
            result = repo.findByPercentageGreaterThanEqualOrderByPercentageDesc(40.0);
        } else {
            result = repo.findByPercentageLessThanOrderByPercentageDesc(40.0);
        }
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(result);
    }

}