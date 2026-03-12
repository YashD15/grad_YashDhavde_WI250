import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GetStudents } from './components/get-students/get-students';
import { AddStudent } from './components/add-student/add-student';
import { Home } from './components/home/home';
import { UpdateStudent } from './components/update-student/update-student';
import { Login } from './components/login/login';
import { GetDetail } from './components/get-detail/get-detail';
import { authGuard } from './guards/auth-guard';

const routes: Routes = [
  {
    path: "",
    component: Home
  },
  {
    path: "login",
    component: Login
  },
  {
    path: "students/details",
    component: GetStudents,
    canActivate: [authGuard]
  },
  {
    path: "students/update/:regNo",
    component: UpdateStudent,
    canActivate: [authGuard]
  },
  {
    path: "students/add",
    component: AddStudent,
    canActivate: [authGuard]
  },
  {
    path: "details",
    component: GetDetail
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
