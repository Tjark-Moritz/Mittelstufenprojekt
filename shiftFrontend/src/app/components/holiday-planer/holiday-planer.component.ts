import { Component, OnInit } from '@angular/core';
import {delay} from "rxjs";
import {GetEmployee} from "../../models/dto/GetEmployee";
import {GetHoliday} from "../../models/dto/GetHoliday";
import {EmployeeService} from "../../services/employee.service";


@Component({
  selector: 'app-holiday-planer',
  templateUrl: './holiday-planer.component.html',
  styleUrls: ['./holiday-planer.component.css']
})

export class HolidayPlanerComponent implements OnInit {

  display: string = "user";
  statusDisplay: string ="normal";

  chossenStartDate: Date = new Date();
  chossenEndDate: Date = new Date();

  // data:HolidayRequest[] = []; // Siehe unten

  displayedColumns = ['vocationStart', 'vocationEnd', 'days', 'statuss'];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
  }

  getUserData():GetEmployee[] {
    let empList: GetEmployee[] = [];
    this.employeeService.getAllEmployees().subscribe(val => empList = val)
    return empList;
  }

  switchUserAdmin()
  {
    this.display = "admin";
  }

  switchUser()
  {
    this.display = "user"
  }

  closeStartPicker(eventData:any, datepicker?:any)
  {
    datepicker.close();
    this.chossenStartDate = eventData.value._d;

  }

  closeEndPicker(eventData:any, datepicker?:any)
  {
  datepicker.close();
  this.chossenEndDate = eventData.value._d;
  }

  requestcounter:number = 0;
  // Die null muss noch duch die id des eingelogten Users ersetzt werden.

  startrequest(){
    let empList = this.getUserData();

    let date: Date = new Date();

    let holidayType: GetHoliday = new GetHoliday()
    holidayType.holidayTypeId = 1; // Nur noch Id

    let holiday: GetHoliday = new GetHoliday();
    holiday.holidayTypeId = 1; // Nur noch Id
    holiday.startDate = this.chossenStartDate;
    holiday.endDate = this.chossenEndDate;

    // Muss Überarbeitet werden; Existiert noch?
    /*
    let holidayRequest: HolidayRequest = new HolidayRequest()
    holidayRequest.requestDate = date;
    holidayRequest.holidayRequestId = this.requestcounter+1;
    holidayRequest.holiday = holiday;
    if (empList[0]) { holidayRequest.requestingEmployeeId = empList[0].id;}
    holidayRequest.status = false;


    this.data.push(holidayRequest);
    */
  }

  holidayaccepted()
  {
    this.statusDisplay = "accepted";
  }

  canceledaccepted()
  {
    this.statusDisplay = "canceled";
  }
}

