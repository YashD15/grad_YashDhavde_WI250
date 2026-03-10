import { Injectable } from '@angular/core';
import { Student } from '../models/student';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  students: Student[] = [
    { regNo: 1, rollNo: 101, name: 'Rahul', standard: '10', school: 'KV' },
    { regNo: 2, rollNo: 102, name: 'Priya', standard: '9', school: 'DPS' },
    { regNo: 3, rollNo: 103, name: 'Amit', standard: '8', school: 'DPS' }
  ];

  getStudents() {
    return this.students;
  }

  getStudent(regNo: number): Student | undefined {
    return this.students.find(student => student.regNo === regNo);
  }

  addStudent(student: Student) {
    this.students.push(student);
  }

  updateStudent(index: number, student: Student) {
    this.students[index] = student;
  }

  deleteStudent(index: number) {
    this.students.splice(index, 1);
  }
}