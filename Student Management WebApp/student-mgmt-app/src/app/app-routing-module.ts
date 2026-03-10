import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { StudentList } from './components/student-list/student-list';
import { StudentForm } from './components/student-form/student-form';
import { loginGuard } from './guards/login-guard-guard';
import { adminGuard } from './guards/admin-guard-guard';
import { Forbidden } from './components/forbidden/forbidden';
import { StudentUpdate } from './components/student-update/student-update';
import { StudentComponent } from './components/student/student';

const routes: Routes = [
  { 
    path: '', 
    redirectTo: 'login', 
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'students',
    component: StudentList,
    canActivate: [loginGuard]
  },
  {
    path: 'student',
    component: StudentComponent
  },
  {
    path: 'student-form',
    component: StudentForm,
    canActivate: [loginGuard, adminGuard]
  },
  {
    path: 'student-update/:index',
    component: StudentUpdate,
    canActivate: [loginGuard, adminGuard]
  },
  {
    path: 'forbidden',
    component: Forbidden
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
