import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Student } from '../models/student.model';
import { Observable } from 'rxjs/internal/Observable';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  role: string | null = null;
  private baseUrl: string = 'http://localhost:8080/students'; 

  constructor(private http: HttpClient, private router: Router) { 
    this.role = localStorage.getItem("role");
  }

  login(creds: any) {
    const username: string = creds.username;
    const password: string = creds.password; 
    if(username === "admin" && password === "admin") {
      localStorage.setItem("role", "ADMIN");
      this.setRole("ADMIN");
      this.router.navigate(['/students/details']);
    }
    else if(username === "staff" && password === "staff") {
      localStorage.setItem("role", "STAFF");
      this.setRole("STAFF");
      this.router.navigate(['/students/details']);
    }
    else {
      alert("Invalid username or password");
    }
  }

  setRole(r: string | null) {
    this.role = r;
  }
  getRole(): string | null {
    return this.role;
  }
  hasRole(): boolean {
    return this.role !== null;
  }
  logout() {
    this.setRole(null);
    localStorage.removeItem("role");
    this.router.navigate(["/"]);
  }

  addStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(this.baseUrl, student);
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.baseUrl);
  }

  deleteStudent(regNo: string): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${regNo}`, {
      responseType: 'text'
    });
  }

  getStudentByRegNo(regNo: string): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${regNo}`);
  }

  updateStudent(student: Student): Observable<Student> {
    return this.http.put<Student>(`${this.baseUrl}/${student.regNo}`, student);
  }

  getStudentsBySchool(schoolName: string): Observable<Student[]> {
    const params = new HttpParams().set('name', schoolName);
    return this.http.get<Student[]>(`${this.baseUrl}/school`, { params });
  }

  getStudentCountBySchool(schoolName: string): Observable<number> {
    const params = new HttpParams().set('name', schoolName);
    return this.http.get<number>(`${this.baseUrl}/school/count`, { params });
  }

  getStudentCountByStandard(standard: number): Observable<string> {
    const params = new HttpParams().set('standard', standard.toString());
    return this.http.get(`${this.baseUrl}/school/standard/count`, {
      params,
      responseType: 'text'
    });
  }

  getStrengthByGenderAndStandard(gender: string, standard: number): Observable<string> {
    const params = new HttpParams()
      .set('gender', gender)
      .set('standard', standard.toString());
    return this.http.get(`${this.baseUrl}/strength`, { params, responseType: 'text' });
  }

  getResultsByPass(pass: boolean): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/result?pass=${pass}`);
  }

}