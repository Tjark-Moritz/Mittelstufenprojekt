import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {AllDepartments} from "../models/dto/AllDepartments";
import {GetDepartment} from "../models/dto/GetDepartment";
import {GetEmployee} from "../models/dto/GetEmployee";
import {GetHoliday} from "../models/dto/GetHoliday";
import {GetSickDay} from "../models/dto/GetSickDay";
import {GetShiftType} from "../models/dto/GetShiftType";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  private urlPre = "/department";
  private bearertoken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJsNTMzXzVOTDJNU2Y4UU50TENXNXF5VTliel9GaDZDZ0ZBT2ktZlZ5WDVjIn0.eyJleHAiOjE2NTE3Mzg2MTksImlhdCI6MTY1MTczMTQxOSwianRpIjoiMjAyYWRmODYtNTRiNy00YTUzLWIwYzQtNjdmODViZjI0YjgyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg5L2F1dGgvcmVhbG1zL1NoaWZ0cGxhbm5lciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI3NmVhZmU4Yi03N2M5LTRjMTEtODU4YS1jMDExODA4MjRlYjEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJzaGlmdHBsYW5uZXIiLCJzZXNzaW9uX3N0YXRlIjoiMGIyZjQ2YmMtZGJmMy00MDA0LWJiYWMtN2QwYWE0ODVmYzUwIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJzaGlmdHVzZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJUZXN0IFVzZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyIiwiZ2l2ZW5fbmFtZSI6IlRlc3QiLCJmYW1pbHlfbmFtZSI6IlVzZXIifQ.h6MZYpxM7Emi3-Yib8rtgK9ZCCTz1U8MYzQ3hKPubbMjoEQxdjW6IeXfJi-EHyRcAxjoulIMDIHAdV65aJw264dYa-s9QuTwlEKsQQRRiJmwNob7HvAuYipNG1Vqrnr9vesyM95STZgVQYN6GhJxExapL9Ue03JErxb1VRgRPlM5Jr9KgyKHLe3KjnQD9hml7c4YjD77oUqgn8MZ2V2KWl1pZCByZSrL8NNRLUkH42-5OZyK8VgwMytJAbCcLwIbH6t_wfDH2zZ6LZ4KlTj86xXL-QI50LK280XW9e4XrJHlzdxUauDiSxrDyMvQqreIe_AiApW--G5Dqjt2J5mXGw";


  // DUMMY
  private depList = new AllDepartments();

  constructor(private httpClient: HttpClient) {

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

    let emplist: GetEmployee[];
    emplist = [emp1, emp2];

    let dep1 = new GetDepartment(1, "department1", "dep1", 1, emplist);
    let dep2 = new GetDepartment(2, "department2", "dep2", 2, emplist);

    this.depList.departmentDto = [dep1, dep2];
  }

  getAllDepartments(): Observable<AllDepartments>{
    return this.httpClient.get<AllDepartments>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    });

    //return of(this.depList);
  }

  getDepartmentById(departmentId: number): Observable<GetDepartment>{
    return this.httpClient.get<GetDepartment>(this.urlPre + `/${departmentId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    });

    // @ts-ignore
    //let dep = this.depList.departments[1];

    //return of(dep);
  }

  addDepartment(department: GetDepartment){
    this.httpClient.post(this.urlPre, {
      "name": department.name,
      "abbreviatedName": department.abbreviatedName,
      "leadEmployee": department.leadEmployee,
      "employees": department.employees,
    },{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    }).toPromise();

    //console.log("added GetDepartment");
  }

  updateDepartment(departmentMap: Map<string, string>, departmentId: number){
    this.httpClient.put(this.urlPre + `/${departmentId}`,departmentMap,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    }).toPromise();

    //console.log("updated GetDepartment");
  }

  deleteDepartment(departmentId: number){
    this.httpClient.delete(this.urlPre + `/${departmentId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    }).toPromise();

    //console.log("deleted GetDepartment");
  }
}
