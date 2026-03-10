import { Component } from '@angular/core';
import { LoginService } from '../../services/login-service';
import { StudentService } from '../../services/student-service';
import { Student } from '../../models/student';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-list',
  standalone: false,
  templateUrl: './student-list.html',
  styleUrl: './student-list.css',
})
export class StudentList {
  students:any[] = [];
  role = '';

  constructor(private studentService: StudentService, private login: LoginService, private router: Router) {
    this.students = studentService.getStudents();
    this.role = login.getRole()!;
  }

  updateStudent(i: number, s: Student) {
    this.router.navigate(['/student-update', i], {
      state: { student: s }
    });
  }

  deleteStudent(i:number){
    if(confirm("Are you sure you wan to delete?"))
      this.studentService.deleteStudent(i);
  }

  logout() {
    if(confirm("Are you sure you want to logout?")) {
      this.login.currentUserRole="";
      this.router.navigate(['/login'])
    }
  }
}
