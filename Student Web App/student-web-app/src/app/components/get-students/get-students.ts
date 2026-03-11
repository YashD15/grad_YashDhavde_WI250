import { Component, OnInit } from '@angular/core';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-get-students',
  standalone: false,
  templateUrl: './get-students.html',
  styleUrl: './get-students.css',
})
export class GetStudents implements OnInit {

  students: Student[] = [];
  displayedStudents: Student[] = [];
  role: string = 'staff'; // static role

  constructor(private studentService: StudentService, private router: Router) { }

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents() {
    this.studentService.getStudents().subscribe({
      next: data => {
        this.students = data;
        this.displayedStudents = data;
      },
      error: err => console.error(err)
    });
  }

  addStudent() {
    this.router.navigate(['/details']);
  }

  updateStudent(student: Student) {
    this.router.navigate(['/students/update', student.regNo]);
  }

  deleteStudent(student: Student) {
    if (confirm(`Are you sure you want to delete ${student.name}?`)) {
      this.studentService.deleteStudent(student.regNo).subscribe({
        next: () => this.loadStudents(),
        error: err => console.error(err)
      });
    }
  }

  // --- API Test Methods ---

  testBySchool() {
    const schoolName = prompt('Enter school name (e.g., KV):');
    if (schoolName) {
      this.studentService.getStudentsBySchool(schoolName).subscribe({
        next: data => this.displayedStudents = data,
        error: err => console.error(err)
      });
    }
  }

  testSchoolCount() {
    const schoolName = prompt('Enter school name (e.g., DPS):');
    if (schoolName) {
      this.studentService.getStudentCountBySchool(schoolName).subscribe({
        next: count => alert(`Total students in ${schoolName}: ${count}`),
        error: err => console.error(err)
      });
    }
  }

  testSchoolStandardCount() {
    const standard = prompt('Enter standard (e.g., 5):');
    if (standard) {
      this.studentService.getStudentCountByStandard(Number(standard)).subscribe({
        next: message => alert(message),
        error: err => console.error(err)
      });
    }
  }

  testStrength() {
    const gender = prompt('Enter gender (MALE/FEMALE/OTHER):');
    const standard = prompt('Enter standard (e.g., 5):');
    if (gender && standard) {
      this.studentService.getStrengthByGenderAndStandard(gender.toUpperCase(), Number(standard)).subscribe({
        next: message => alert(message),
        error: err => console.error(err)
      });
    }
  }

  testResult() {
    const pass = confirm('Do you want to get students who passed? OK=Pass, Cancel=Fail');
    this.studentService.getResultsByPass(pass).subscribe({
      next: data => {
        this.displayedStudents = data; // Update table for List<Student>
      },
      error: err => console.error(err)
    });
  }
}
