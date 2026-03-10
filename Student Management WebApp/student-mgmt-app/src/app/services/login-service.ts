import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  
  currentUserRole: string | null = null;

  login(username: string, password: string) {

    if(username === 'admin' && password === 'admin'){
      this.currentUserRole = 'admin';
      return true;
    }

    if(username === 'staff' && password === 'staff'){
      this.currentUserRole = 'staff';
      return true;
    }

    return false;
  }

  getRole(){
    return this.currentUserRole;
  }

  isLoggedIn(){
    return this.currentUserRole != null;
  }

  logout(){
    this.currentUserRole = null;
  }
}
