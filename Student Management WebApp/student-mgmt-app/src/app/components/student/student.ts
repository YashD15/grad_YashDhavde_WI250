import { Component } from '@angular/core';
import { StudentService } from '../../services/student-service';
import { Student } from '../../models/student';

@Component({
  selector: 'app-student',
  standalone: false,
  templateUrl: './student.html',
  styleUrl: './student.css',
})
export class StudentComponent {
  selectedStudent?: Student;
  searched = false;

  constructor(private studentService: StudentService) {}

  searchStudent(regNoValue: string) {

    const regNo = Number(regNoValue);

    this.selectedStudent = this.studentService.getStudent(regNo);

    this.searched = true;
  }
}
