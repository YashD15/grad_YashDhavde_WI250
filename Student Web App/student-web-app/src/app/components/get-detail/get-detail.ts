import { Component } from '@angular/core';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student-service';

@Component({
  selector: 'app-get-detail',
  standalone: false,
  templateUrl: './get-detail.html',
  styleUrl: './get-detail.css',
})
export class GetDetail {
  regNo: string = '';
  student: Student | null = null;
  searched: boolean = false;

  constructor(private ss: StudentService) {}
  
  searchStudent(regNo: string) {
  this.ss.getStudentByRegNo(regNo).subscribe({
    next: (res) => {
      this.student = res;
      this.searched = true;
    },
    error: (err) => {
      console.error(err);
      this.student = null;
      this.searched = true;
    }
  });
}
}
