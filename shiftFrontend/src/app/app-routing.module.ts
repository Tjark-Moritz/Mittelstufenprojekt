import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./components/login/login.component";
import {DepartmentListComponent} from "./components/department-list/department-list.component";
import {EmployeeListComponent} from "./components/employee-list/employee-list.component";
import {HolidayPlanerComponent} from "./components/holiday-planer/holiday-planer.component";
import {ShiftPlanComponent} from "./components/shift-plan/shift-plan.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {AuthenticationGuardService} from "./services/authentication-guard.service";

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'index', redirectTo: '/shift', pathMatch:'full'},
  {path:'deps', component: DepartmentListComponent, canActivate:[AuthenticationGuardService]},
  {path:'emps', component: EmployeeListComponent, canActivate:[AuthenticationGuardService]},
  {path:'holiday', component: HolidayPlanerComponent, canActivate:[AuthenticationGuardService]},
  {path:'shift', component: ShiftPlanComponent, canActivate:[AuthenticationGuardService]},
  {path:'', redirectTo: '/login', pathMatch: 'full'},
  {path:'**', component: PageNotFoundComponent}
];

@NgModule({
  declarations: [],
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {

}
