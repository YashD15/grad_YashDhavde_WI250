import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../services/student-service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  loginData = {
    username: '',
    password: ''
  };

  constructor(private ss: StudentService) {}

  onSubmit() {
    this.ss.login(this.loginData);
  }
}
