import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetEmployee} from "../models/dto/GetEmployee";
import {AddEmployee} from "../models/dto/AddEmployee";
import {BearerTokenService} from "./bearer-token.service";
import {UpdatedPassword} from "../models/dto/UpdatedPassword";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private urlPre = "/employee"

  constructor(private httpClient: HttpClient, private bearerTokenService: BearerTokenService) {

  }

  getAllEmployees(): Observable<GetEmployee[]>{
    return this.httpClient.get<GetEmployee[]>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  getEmployeeById(employeeId: number): Observable<GetEmployee>{
    return this.httpClient.get<GetEmployee>(this.urlPre + `/${employeeId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  getEmployeeByUsername(username: string): Observable<GetEmployee>{
    return this.httpClient.get<GetEmployee>(this.urlPre + `/username/${username}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  updateEmployeePassword(updatedPassword: UpdatedPassword){
    this.httpClient.post(this.urlPre + `/password`, updatedPassword,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  updateEmployeePasswordById(updatedPassword: UpdatedPassword, employeeId: number){
    this.httpClient.post(this.urlPre + `/${employeeId}/password`, updatedPassword,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  addEmployee(employee: AddEmployee){
    this.httpClient.post(this.urlPre, employee,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  deleteEmployee(employeeId: number){
    this.httpClient.delete(this.urlPre + `/${employeeId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();

  }

  updateEmployee(employeeChanges: {[key: string]: object}, employeeId: number){
    this.httpClient.patch(this.urlPre + `/${employeeId}`, employeeChanges,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  changeEmployeeRole(role: string, employeeId: number){
    this.httpClient.patch(this.urlPre + `/${employeeId}/role`, role,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  async getEmployeeIdByFirstAndLastName(firstName: string, lastName: string): Promise<number>{
    let emp: GetEmployee | undefined;
    let empArray = await this.getAllEmployees().toPromise();
    emp = empArray?.find(res => {
      if(res.firstName == firstName && res.lastName == lastName){
        return res;
      }
      return;
    });
    if(emp && emp.id){
      return emp?.id;
    }
    return -1;
  }
}
