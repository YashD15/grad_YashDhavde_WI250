package com.example.studentsrestapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.studentsrestapp.controller.StudentController;
import com.example.studentsrestapp.entities.Student;
import com.example.studentsrestapp.repository.StudentRepo;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepo repo;

    // GET /students
    @Test
    void testGetAllStudents() throws Exception {

        Student s = new Student("R101", "10", "Rahul",
                8, "DPS", "MALE", 78.5);

        when(repo.findAll()).thenReturn(Arrays.asList(s));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rahul"));
    }

    @Test
    void testGetAllStudents_Empty() throws Exception {

        when(repo.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/students"))
                .andExpect(status().isNoContent());
    }

    // GET /students/{regNo}
    @Test
    void testGetByRegNo_Found() throws Exception {

        Student s = new Student("R111", "22", "Anita",
                9, "KV", "FEMALE", 88.0);

        when(repo.findById("R111")).thenReturn(Optional.of(s));

        mockMvc.perform(get("/students/R111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.school").value("KV"));
    }

    @Test
    void testGetByRegNo_NotFound() throws Exception {

        when(repo.findById("XYZ")).thenReturn(Optional.empty());

        mockMvc.perform(get("/students/XYZ"))
                .andExpect(status().isNoContent());
    }

    // POST /students
    @Test
    void testCreateStudent() throws Exception {

        Student s = new Student("R123", "33", "Meena",
                7, "KV", "FEMALE", 91.0);

        when(repo.existsById("R123")).thenReturn(false);
        when(repo.save(any(Student.class))).thenReturn(s);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "regNo": "R123",
                          "rollNo": "33",
                          "name": "Meena",
                          "standard": 7,
                          "school": "KV",
                          "gender": "FEMALE",
                          "percentage": 91.0
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Meena"));
    }

    @Test
    void testCreateStudent_Conflict() throws Exception {

        when(repo.existsById("R321")).thenReturn(true);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "regNo": "R321"
                        }
                        """))
                .andExpect(status().isConflict());
    }

    // PUT /students/{regNo}
    @Test
    void testUpdateStudent() throws Exception {

        when(repo.existsById("R132")).thenReturn(true);
        when(repo.save(any(Student.class)))
                .thenReturn(new Student("R132", "44", "UpdatedName",
                        10, "KV", "MALE", 65.0));

        mockMvc.perform(put("/students/R132")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "rollNo": "44",
                          "name": "UpdatedName",
                          "standard": 10,
                          "school": "KV",
                          "gender": "MALE",
                          "percentage": 65.0
                        }
                        """))
                .andExpect(status().isOk());
    }

    // DELETE /students/{regNo}
    @Test
    void testDeleteStudent() throws Exception {

        when(repo.existsById("R001")).thenReturn(true);
        doNothing().when(repo).deleteById("R001");

        mockMvc.perform(delete("/students/R001"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student record Deleted"));
    }

    // GET /students/school
    @Test
    void testGetBySchool() throws Exception {

        Student s = new Student("R100", "66", "Karan",
                6, "KV", "MALE", 72.0);

        when(repo.findBySchool("KV"))
                .thenReturn(Arrays.asList(s));

        mockMvc.perform(get("/students/school")
                .param("name", "KV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Karan"));
    }

    // GET /students/school/count
    @Test
    void testCountBySchool() throws Exception {

        when(repo.countBySchool("KV")).thenReturn(4L);

        mockMvc.perform(get("/students/school/count")
                .param("name", "KV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4));
    }

    // GET /students/school/standard/count
    @Test
    void testCountByStandard() throws Exception {

        when(repo.countByStandard(5)).thenReturn(12L);

        mockMvc.perform(get("/students/school/standard/count")
                .param("standard", "5"))
                .andExpect(status().isOk());
    }

    // GET /students/strength
    @Test
    void testStrength() throws Exception {

        when(repo.countByGenderAndStandard("MALE", 7))
                .thenReturn(6L);

        mockMvc.perform(get("/students/strength")
                .param("gender", "MALE")
                .param("standard", "7"))
                .andExpect(status().isOk());
    }

    // PATCH /students/{regNo}
    @Test
    void testPatchStudent() throws Exception {

        Student old = new Student("R123", "77", "RAhul",
                8, "KV", "MALE", 55.0);

        when(repo.findById("R123")).thenReturn(Optional.of(old));
        when(repo.save(any(Student.class))).thenReturn(old);

        mockMvc.perform(patch("/students/R123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Rahul"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Patched"));
    }

    // GET /students/result?pass=true
    @Test
    void testResultPass() throws Exception {

        Student s = new Student("R122", "88", "Priya",
                9, "KV", "FEMALE", 92.0);

        when(repo.findByPercentageGreaterThanEqualOrderByPercentageDesc(40.0))
                .thenReturn(Arrays.asList(s));

        mockMvc.perform(get("/students/result")
                .param("pass", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Priya"));
    }

    @Test
    void testResultFail() throws Exception {

        when(repo.findByPercentageLessThanOrderByPercentageDesc(40.0))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/students/result")
                .param("pass", "false"))
                .andExpect(status().isNoContent());
    }
}