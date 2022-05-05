import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { DepartmentListComponent } from './components/department-list/department-list.component';
import { HighliterPipe } from './pipes/highliter.pipe';
import { EmployeeListComponent } from './components/employee-list/employee-list.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './components/login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import { HolidayPlanerComponent } from './components/holiday-planer/holiday-planer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatTableModule} from '@angular/material/table';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatNativeDateModule} from "@angular/material/core";
import {MatDateFnsModule} from "@angular/material-date-fns-adapter";
import {MatMomentDateModule} from "@angular/material-moment-adapter";

import { ShiftPlanComponent } from './components/shift-plan/shift-plan.component';
import {MatInputModule} from "@angular/material/input";
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';

//MatLuxonDateModule

@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    HighliterPipe,
    NavbarComponent,
    HolidayPlanerComponent,
    DepartmentListComponent,
    ShiftPlanComponent,
    LoginComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatDateFnsModule,
    MatMomentDateModule,
    MatInputModule,
    AppRoutingModule
  ],
  providers: [HighliterPipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
