import { Component } from '@angular/core';
import { StudentService } from '../../services/student-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-form',
  standalone: false,
  templateUrl: './student-form.html',
  styleUrl: './student-form.css',
})
export class StudentForm {

  constructor(private studentService: StudentService, private router: Router) {}

  addStudent(event: any) {

    const student = {
      regNo: event.target.elements[0].value,
      rollNo: event.target.elements[1].value,
      name: event.target.elements[2].value,
      standard: event.target.elements[3].value,
      school: event.target.elements[4].value
    }
    this.studentService.addStudent(student);
    this.router.navigate(['/students']);
  }

  cancel() {
    this.router.navigate(['/students']);
  }
}
