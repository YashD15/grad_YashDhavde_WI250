import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login-service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  
  constructor(private log: LoginService, private router: Router){}

  login(event: any){

    const username = event.target.elements[0].value;
    const password = event.target.elements[1].value;

    if(this.log.login(username,password)){
      this.router.navigate(['/students']);
    }
    else{
      alert("Invalid credentials");
    }
  }
}
