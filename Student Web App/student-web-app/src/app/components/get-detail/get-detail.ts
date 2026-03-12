import { Component } from '@angular/core';
import { Student } from '../../models/student.model';

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

  // Mock data; replace with API call
  students: Student[] = [
    { regNo: 'R001', rollNo: 1, name: 'Alice', standard: 10, school: 'ABC School', gender: 'Female', percentage: 85 }
  ];

  searchStudent() {
    this.student = this.students.find(s => s.regNo === this.regNo) || null;
    this.searched = true;
  }
}
