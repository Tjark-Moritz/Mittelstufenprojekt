import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetEmployee} from "../models/dto/GetEmployee";
import {AddEmployee} from "../models/dto/AddEmployee";
import {BearerTokenService} from "./bearer-token.service";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private urlPre = "/employee"

  constructor(private httpClient: HttpClient) {

  }

  getAllEmployees(): Observable<GetEmployee[]>{
    return this.httpClient.get<GetEmployee[]>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    });
  }

  getEmployeeById(employeeId: number): Observable<GetEmployee>{
    return this.httpClient.get<GetEmployee>(this.urlPre + `/${employeeId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    });
  }

  addEmployee(employee: AddEmployee){
    this.httpClient.post(this.urlPre, employee,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    }).toPromise();

    console.log("added Employee");
  }

  deleteEmployee(employeeId: number){
    this.httpClient.delete(this.urlPre + `/${employeeId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    }).toPromise();

    console.log("deleted Employee");
  }

  updateEmployee(employeeMap: Map<string, string>, employeeId: number){
    this.httpClient.patch(this.urlPre + `/${employeeId}`, employeeMap,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${BearerTokenService.bearerToken.access_token}`)
    }).toPromise();

    console.log("updated Employee");
  }

  getEmployeeIdByFirstAndLastName(firstName: string, lastName: string): number{
    this.getAllEmployees().subscribe(res => {
      let emp = res.find(element => {
        if(element.firstName == firstName && element.lastName == lastName){
          return true;
        }
        return false;
      });
      if(emp){
        return emp.id;
      }
      else {
        return -1;
      }
    });
    return -1;
  }
}
