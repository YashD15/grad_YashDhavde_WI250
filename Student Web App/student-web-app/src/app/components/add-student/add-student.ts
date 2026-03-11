import { Component } from '@angular/core';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-student',
  standalone: false,
  templateUrl: './add-student.html',
  styleUrl: './add-student.css',
})
export class AddStudent {
  student: Student = {
    regNo: '',
    rollNo: 0,
    name: '',
    standard: 0,
    school: '',
    gender: '',
    percentage: 0
  };

  constructor(private studentService: StudentService, private router: Router) {}

  addStudent() {
    this.studentService.addStudent(this.student).subscribe({
      next: (res) => {
        alert('Student added successfully!');
        this.router.navigate(['/']);
      },
      error: (err) => {
        alert('Error adding student. Please try again.');
        console.error(err);
      }
    });
  }

  updateStudent() {}

  cancel() {
    this.router.navigate(['/']);
  }
}
