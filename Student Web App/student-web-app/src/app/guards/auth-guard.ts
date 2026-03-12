import { CanActivateFn, Router } from '@angular/router';
import { StudentService } from '../services/student-service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const ss: StudentService = inject(StudentService);
  const router: Router = inject(Router);
  if(ss.hasRole()) {
    return true;
  }
  return router.createUrlTree(['/login']);
};
