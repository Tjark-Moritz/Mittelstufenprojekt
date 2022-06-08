import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {HttpClientModule} from "@angular/common/http";
import { HighliterPipe } from './pipes/highliter.pipe';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatTableModule} from '@angular/material/table';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatNativeDateModule} from "@angular/material/core";
import {MatDateFnsModule} from "@angular/material-date-fns-adapter";
import {MatMomentDateModule} from "@angular/material-moment-adapter";
import {MatInputModule} from "@angular/material/input";
import { MatCheckboxModule } from "@angular/material/checkbox";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatDialogModule} from "@angular/material/dialog";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatIconModule} from "@angular/material/icon";

import { AppComponent } from './app.component';
import { DepartmentListComponent } from './components/department-list/department-list.component';
import { EmployeeListComponent } from './components/employee-list/employee-list.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './components/login/login.component';
import { HolidayPlanerComponent } from './components/holiday-planer/holiday-planer.component';
import { DayDetailsComponent } from './components/day-details/day-details.component';
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { DepartmentDetailsComponent } from './components/department-details/department-details.component';
import { ShiftPlanComponent } from './components/shift-plan/shift-plan.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import { EmployeeDetailsComponent } from './components/employee-details/employee-details.component';

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
    DayDetailsComponent,
    DepartmentDetailsComponent,
    PageNotFoundComponent
    LoginComponent,
    PageNotFoundComponent,
    EmployeeDetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatTableModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatDateFnsModule,
    MatMomentDateModule,
    MatInputModule,
    AppRoutingModule,
    MatDialogModule,
    MatExpansionModule,
    MatIconModule,
    MatListModule,
    FormsModule,
    AppRoutingModule,
    MatSidenavModule,
    MatListModule
  ],
  providers: [HighliterPipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
