import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GetStudents } from './components/get-students/get-students';
import { AddStudent } from './components/add-student/add-student';
import { Home } from './components/home/home';
import { UpdateStudent } from './components/update-student/update-student';

const routes: Routes = [
  {
    path: "",
    component: Home
  },
  {
    path: "login",
    component: AddStudent
  },
  {
    path: "details",
    component: GetStudents
  },
  {
    path: "students/update/:regNo",
    component: UpdateStudent
  },
  {
    path: "students",
    component: GetStudents
  },
  {
    path: "students/add",
    component: AddStudent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
