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

    // @ts-ignore
    let shi = department.shiftTypes[0].shiftStartTime.getHours();
    // @ts-ignore
    let smi = department.shiftTypes[0].shiftStartTime.getHours();
    // @ts-ignore
    let ssi = department.shiftTypes[0].shiftStartTime.getHours();
    // @ts-ignore
    let ehi = department.shiftTypes[0].shiftEndTime.getHours();
    // @ts-ignore
    let emi = department.shiftTypes[0].shiftEndTime.getHours();
    // @ts-ignore
    let esi = department.shiftTypes[0].shiftEndTime.getHours();

    let sh, sm, ss, eh, em, es;


    if(shi < 10){
      sh = "0" + String(shi);
    }
    else {
      sh = String(shi);
    }
    if(smi < 10){
      sm = "0" + String(smi);
    }
    else {
      sm = String(smi);
    }
    if(ssi < 10){
      ss = "0" + String(ssi);
    }
    else {
      ss = String(ssi);
    }

    if(ehi < 10){
      eh = "0" + String(ehi);
    }
    else {
      eh = String(ehi);
    }
    if(emi < 10){
      em = "0" + String(emi);
    }
    else {
      em = String(emi);
    }
    if(esi < 10){
      es = "0" + String(esi);
    }
    else {
      es = String(esi);
    }

    // @ts-ignore
    let sdate: string =  sh + ":" + sm + ":" + ss;
    // @ts-ignore
    let edate: string = eh + ":" + em + ":" + es;

    this.httpClient.post(this.urlPre, {
      name: department.name,
      abbreviatedName: department.abbreviatedName,
      leadEmployeeId: department.leadEmployeeId,
      employeeIds: department.employeeIds,
      shiftTypes: [{
        shiftStartTime: sdate,
        shiftEndTime: edate,
        // @ts-ignore
        targetNumOfEmps: department.shiftTypes[0].targetNumOfEmps,
        // @ts-ignore
        typeName: department.shiftTypes[0].typeName,
        // @ts-ignore
        shiftTypeColor: department.shiftTypes[0].shiftTypeColor
      }]
    },{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

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
}
