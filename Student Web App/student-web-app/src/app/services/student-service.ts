import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Student } from '../models/student.model';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  private baseUrl = 'http://localhost:8080/students'; // Replace with your Spring Boot URL

  constructor(private http: HttpClient) { }

  addStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(this.baseUrl, student);
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.baseUrl);
  }

  deleteStudent(regNo: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${regNo}`);
  }

  getStudentByRegNo(regNo: string): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${regNo}`);
  }

  updateStudent(student: Student): Observable<Student> {
    return this.http.put<Student>(`${this.baseUrl}/${student.regNo}`, student);
  }

  // New API endpoints
  getStudentsBySchool(schoolName: string): Observable<Student[]> {
    const params = new HttpParams().set('name', schoolName);
    return this.http.get<Student[]>(`${this.baseUrl}/school`, { params });
  }

  getStudentCountBySchool(schoolName: string): Observable<number> {
    const params = new HttpParams().set('name', schoolName);
    return this.http.get<number>(`${this.baseUrl}/school/count`, { params });
  }

  getStudentCountByStandard(standard: number): Observable<string> {
  const params = new HttpParams().set('standard', standard);
  return this.http.get<string>(`${this.baseUrl}/school/standard/count`, { params });
}

getStrengthByGenderAndStandard(gender: string, standard: number): Observable<string> {
  const params = new HttpParams()
    .set('gender', gender)
    .set('standard', standard.toString());
  return this.http.get<string>(`${this.baseUrl}/strength`, { params });
}

getResultsByPass(pass: boolean): Observable<Student[]> {
  return this.http.get<Student[]>(`${this.baseUrl}/result?pass=${pass}`);
}

}
