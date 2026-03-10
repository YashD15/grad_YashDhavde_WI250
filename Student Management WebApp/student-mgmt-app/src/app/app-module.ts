import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Login } from './components/login/login';
import { StudentList } from './components/student-list/student-list';
import { StudentForm } from './components/student-form/student-form';
import { Forbidden } from './components/forbidden/forbidden';
import { StudentUpdate } from './components/student-update/student-update';
import { StudentComponent } from './components/student/student';

@NgModule({
  declarations: [App, Login, StudentList, StudentForm, Forbidden, StudentUpdate, StudentComponent],
  imports: [BrowserModule, AppRoutingModule],
  providers: [provideBrowserGlobalErrorListeners()],
  bootstrap: [App],
})
export class AppModule {}
