import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {AllEmployees} from "../models/dto/AllEmployees";
import {Employee} from "../models/dto/Employee";
import {Holiday} from "../models/dto/Holiday";
import {HolidayType} from "../models/dto/HolidayType";
import {SickDay} from "../models/dto/SickDay";
import {ShiftType} from "../models/dto/ShiftType";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private urlPre = "/employees"

  // DUMMY
  private emplist = new AllEmployees();
  private bearertoken = "";

  constructor(private httpClient: HttpClient) {


    let holType1 = new HolidayType(1, "Urlaub");
    let holType2 = new HolidayType(2, "Sonderurlaub");
    let holType3 = new HolidayType(3, "Bildungsurlaub");

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

    let hol1 = new Holiday(1, holType1, dat1, dat2);
    let hol2 = new Holiday(2, holType2, dat3, dat4);
    let hol3 = new Holiday(3, holType3, dat5, dat6);
    let hol4 = new Holiday(4, holType3, dat5, dat6);
    let hol5 = new Holiday(5, holType2, dat3, dat4);
    let hol6 = new Holiday(6, holType1, dat1, dat2);

    let holList1: Holiday[] = [hol1, hol2, hol3];
    let holList2: Holiday[] = [hol4, hol5, hol6];

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

    let sickDay1 = new SickDay(1, date1, date2);
    let sickDay2 = new SickDay(2, date3, date4);
    let sickDay3 = new SickDay(3, date5, date6);

    let sickDayList1: SickDay[] = [sickDay1, sickDay2];
    let sickDayList2: SickDay[] = [sickDay3];

    let sdate1 = new Date();
    sdate1.setHours(6,0,0,0);
    let sdate2 = new Date();
    sdate2.setHours(14,0,0,0);
    let sdate3 = new Date();
    sdate3.setHours(14,0,0,0);
    let sdate4 = new Date();
    sdate4.setHours(22,0,0,0);

    let shifttype1 = new ShiftType(1, sdate1, sdate2);
    let shifttype2 = new ShiftType(2, sdate3, sdate4);

    let emp1 = new Employee(1, "fn1", "ln1", "addr1", "zip1", "city1", "tel1", "email1", holList1, sickDayList1, 10, "", shifttype1, 1 );
    let emp2 = new Employee(2, "fn2", "ln2", "addr2", "zip2", "city2", "tel2", "email2", holList2, sickDayList2, 10, "", shifttype2, 2);

    this.emplist.employees = [emp1, emp2];
  }

  getAllEmployees(): Observable<AllEmployees>{
    /*
    return this.httpClient.get<AllEmployees[]>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    });
    */

    return of(this.emplist);
  }

  getEmployeeById(employee: Employee): Observable<Employee>{
    /*
    return this.httpClient.get<Employee>(this.urlPre + `/${employee.employeeId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    });
    */

    // @ts-ignore
    let emp = this.emplist.employees[1];
    return of(emp);
  }

  addEmployee(employee: Employee){
    /*
    this.httpClient.post(this.urlPre,{
      'firstName': employee.firstname,
      'lastName': employee.lastname,
      'address': employee.address,
      'zipcode': employee.zipcode,
      'city': employee.city,
      'telephone': employee.telephone,
      'email': employee.email,
      'numHolidaysLeft': employee.numHolidaysLeft,
      'preferredShiftType': employee.preferredShiftType,
      'encodededProfilePic': employee.encodededProfilePic,
      'departmentId': employee.departmentId,
    },{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    }).toPromise();
    */

    console.log("added Employee");
  }

  deleteEmployee(employee: Employee){
    /*
    this.httpClient.delete(this.urlPre + `/${employee.employeeId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    }).toPromise();
    */

    console.log("deleted Employee");
  }

  updateEmployee(employee: Employee){
    /*
    this.httpClient.put(this.urlPre + `/${employee.employeeId}`,employee,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearertoken}`)
    }).toPromise();
    */
    console.log("updated Employee");
  }
}
