import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/login-service';
import { inject } from '@angular/core';

export const loginGuard: CanActivateFn = (route, state) => {

  let login: LoginService = inject(LoginService)
  let router: Router = inject(Router)

  if(login.isLoggedIn()){
    return true;
  }

  router.navigate(['/forbidden']);
  return false;
};
