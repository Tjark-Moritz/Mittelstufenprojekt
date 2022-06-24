import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetDepartment} from "../models/dto/GetDepartment";
import {BearerTokenService} from "./bearer-token.service";
import {AddDepartment} from "../models/dto/AddDepartment";
import {GetShiftType} from "../models/dto/GetShiftType";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  private urlPre = "/department";

  constructor(private httpClient: HttpClient, private bearerTokenService: BearerTokenService) {
  }

  getAllDepartments(): Observable<GetDepartment[]>{
    return this.httpClient.get<GetDepartment[]>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  getDepartmentById(departmentId: number): Observable<GetDepartment>{
    return this.httpClient.get<GetDepartment>(this.urlPre + `/${departmentId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  addDepartment(department: AddDepartment){

    let s1: string = '"name": "' + department.name + '",' +
      '"abbreviatedName": "' +department.abbreviatedName + '",' +
      '"leadEmployeeId": "' + department.leadEmployeeId + '",' +
      '"employeeIds": [' + department.employeeIds + '],' +
      '"shiftTypes": [';
    let s1_1: string = "";
    let s2: string = ']';
    if(department.shiftTypes){
      let isNotFirst: boolean = false;
      department.shiftTypes.forEach(res => {
        let s_1: string = "";
        if(!isNotFirst){
          // @ts-ignore
          s_1 = '{"shiftStartTime": "' + res.shiftStartTime.toLocaleTimeString() + '",';
        }
        else {
          // @ts-ignore
          s_1 = ',{"shiftStartTime": "' + res.shiftStartTime.toLocaleTimeString() + '",';
        }
        isNotFirst = true;
        // @ts-ignore
        let s_2: string = '"shiftEndTime": "' + res.shiftEndTime.toLocaleTimeString() + '",';
        let s_3: string = '"targetNumOfEmps": "' + res.targetNumOfEmps + '",';
        let s_4: string = '"typeName": "' + res.typeName + '",';
        let s_5: string = '"shiftTypeColor": "' + res.shiftTypeColor + '"}';
        let s_ges: string = s_1 + s_2 + s_3 + s_4 + s_5;
        s1_1 += s_ges;
      });
    }
    let sges = "{" + s1 + s1_1 + s2 + "}";

    this.httpClient.post(this.urlPre, sges,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  //todo
  updateDepartment(depChanges: {[key: string]: string}, departmentId: number){
    this.httpClient.patch(this.urlPre + `/${departmentId}`,depChanges,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  deleteDepartment(departmentId: number){
    this.httpClient.delete(this.urlPre + `/${departmentId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  getShifttypesFromDepartment(departmentId: number){
    return this.httpClient.get<GetShiftType[]>(this.urlPre + `/${departmentId}/shifttypes`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }
}
