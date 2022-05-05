import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./components/login/login.component";
import {DepartmentListComponent} from "./components/department-list/department-list.component";
import {EmployeeListComponent} from "./components/employee-list/employee-list.component";
import {HolidayPlanerComponent} from "./components/holiday-planer/holiday-planer.component";
import {ShiftPlanComponent} from "./components/shift-plan/shift-plan.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'departments', component: DepartmentListComponent},
  {path:'employees', component: EmployeeListComponent},
  {path:'holiday', component: HolidayPlanerComponent},
  {path:'shift', component: ShiftPlanComponent},
  {path:'', redirectTo: '/login', pathMatch: 'full'},
  {path:'**', component: PageNotFoundComponent}
];

@NgModule({
  declarations: [],
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
