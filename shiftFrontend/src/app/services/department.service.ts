import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetDepartment} from "../models/dto/GetDepartment";
import {BearerTokenService} from "./bearer-token.service";
import {AddDepartment} from "../models/dto/AddDepartment";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  private urlPre = "/department";

  constructor(private httpClient: HttpClient) {
  }

  getAllDepartments(): Observable<GetDepartment[]>{
    return this.httpClient.get<GetDepartment[]>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    });
  }

  getDepartmentById(departmentId: number): Observable<GetDepartment>{
    return this.httpClient.get<GetDepartment>(this.urlPre + `/${departmentId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    });
  }

  addDepartment(department: AddDepartment){
    this.httpClient.post(this.urlPre, {
      "name": department.name,
      "abbreviatedName": department.abbreviatedName,
      "leadEmployeeId": department.leadEmployeeId,
      "employeeIds": department.employeesIds,
    },{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  updateDepartment(departmentMap: Map<string, string>, departmentId: number){
    this.httpClient.patch(this.urlPre + `/${departmentId}`,departmentMap,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  deleteDepartment(departmentId: number){
    this.httpClient.delete(this.urlPre + `/${departmentId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }
}
