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

  constructor(private ss: StudentService, private router: Router) {}

  addStudent(): void {
    if(this.student.regNo == '' || 
      this.student.rollNo == 0 ||
      this.student.name == '' ||
      this.student.standard == 0 ||
      this.student.school == '' ||
      this.student.gender == '' ||
      this.student.percentage == 0
    ) {
      alert("Empty field detected in the form");
    } else {
      this.ss.addStudent(this.student).subscribe({
        next: (res) => {
          alert('Student added successfully!');
          this.router.navigate(['/students/details']);
        },
        error: (err) => {
          alert('Error adding student. Please try again.');
          console.error(err);
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/students/details']);
  }
}
