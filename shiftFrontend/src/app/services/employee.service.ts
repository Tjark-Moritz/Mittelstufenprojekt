import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {GetEmployee} from "../models/dto/GetEmployee";
import {GetHoliday} from "../models/dto/GetHoliday";
import {GetSickDay} from "../models/dto/GetSickDay";
import {GetShiftType} from "../models/dto/GetShiftType";
import {AddEmployee} from "../models/dto/AddEmployee";
import {BearerTokenService} from "./bearer-token.service";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private urlPre = "/employee"

  // DUMMY
  //private emplist: GetEmployee[];

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
}



/*
    let dat1 = new Date();
    dat1.setFullYear(2020, 2, 16);
    let dat2 = new Date();
    dat2.setFullYear(2020, 3, 16);
    let dat3 = new Date();
    dat3.setFullYear(2021, 2, 16);
    let dat4 = new Date();
    dat4.setFullYear(2021, 3, 16);
    let dat5 = new Date();
    dat5.setFullYear(2022, 2, 16);
    let dat6 = new Date();
    dat6.setFullYear(2022, 3, 16);

    let hol1 = new GetHoliday(1, 1, dat1, dat2);
    let hol2 = new GetHoliday(2, 2, dat3, dat4);
    let hol3 = new GetHoliday(3, 3, dat5, dat6);
    let hol4 = new GetHoliday(4, 1, dat5, dat6);
    let hol5 = new GetHoliday(5, 2, dat3, dat4);
    let hol6 = new GetHoliday(6, 3, dat1, dat2);

    let holList1: GetHoliday[] = [hol1, hol2, hol3];
    let holList2: GetHoliday[] = [hol4, hol5, hol6];

    let date1 = new Date();
    date1.setFullYear(2020, 4, 16);
    let date2 = new Date();
    date2.setFullYear(2020, 5, 16);
    let date3 = new Date();
    date3.setFullYear(2021, 4, 16);
    let date4 = new Date();
    date4.setFullYear(2021, 5, 16);
    let date5 = new Date();
    date5.setFullYear(2022, 4, 16);
    let date6 = new Date();
    date6.setFullYear(2022, 5, 16);

    let sickDay1 = new GetSickDay();
    let sickDay2 = new GetSickDay();
    let sickDay3 = new GetSickDay();

    let sickDayList1: GetSickDay[] = [sickDay1, sickDay2];
    let sickDayList2: GetSickDay[] = [sickDay3];

    let sdate1 = new Date();
    sdate1.setHours(6,0,0,0);
    let sdate2 = new Date();
    sdate2.setHours(14,0,0,0);
    let sdate3 = new Date();
    sdate3.setHours(14,0,0,0);
    let sdate4 = new Date();
    sdate4.setHours(22,0,0,0);

    let shifttype1 = new GetShiftType(1, sdate1, sdate2);
    let shifttype2 = new GetShiftType(2, sdate3, sdate4);

    let emp1 = new GetEmployee(1,"u1", "fn1", "ln1", "addr1", "zip1", "city1", "tel1", "email1", 0, holList1, sickDayList1, "10", shifttype1, 1);
    let emp2 = new GetEmployee(2,"u2", "fn2", "ln2", "addr2", "zip2", "city2", "tel2", "email2", 0, holList2, sickDayList2, "10", shifttype2, 2);

    this.emplist = [emp1, emp2];
    */
