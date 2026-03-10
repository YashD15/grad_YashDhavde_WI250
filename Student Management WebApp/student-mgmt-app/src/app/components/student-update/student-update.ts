import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Student } from '../../models/student';
import { StudentService } from '../../services/student-service';

@Component({
  selector: 'app-student-update',
  standalone: false,
  templateUrl: './student-update.html',
  styleUrl: './student-update.css',
})
export class StudentUpdate implements OnInit {

  student!: Student;
  index!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studentService: StudentService
  ) {}

  ngOnInit() {
    // Get index from route
    this.index = Number(this.route.snapshot.paramMap.get('index'));

    // Get student object from navigation state
    const state = history.state;
    this.student = state.student;
  }

  updateStudent(regNo:any, rollNo:any, name:any, standard:any, school:any){

  const updatedStudent = {
    regNo: regNo,
    rollNo: rollNo,
    name: name,
    standard: standard,
    school: school
  };

  this.studentService.updateStudent(this.index, updatedStudent);
  this.router.navigate(['/students']);
}

  cancel() {
    this.router.navigate(['/students']);
  }

}
