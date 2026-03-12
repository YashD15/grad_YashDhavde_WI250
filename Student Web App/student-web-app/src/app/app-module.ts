import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { GetStudents } from './components/get-students/get-students';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AddStudent } from './components/add-student/add-student';
import { Home } from './components/home/home';
import { UpdateStudent } from './components/update-student/update-student';
import { Login } from './components/login/login';
import { Navbar } from './components/navbar/navbar';
import { GetDetail } from './components/get-detail/get-detail';
import { StudentService } from './services/student-service';

@NgModule({
  declarations: [App, GetStudents, AddStudent, Home, UpdateStudent, Login, Navbar, GetDetail],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [provideBrowserGlobalErrorListeners(), HttpClient, StudentService],
  bootstrap: [App],
})
export class AppModule {}
