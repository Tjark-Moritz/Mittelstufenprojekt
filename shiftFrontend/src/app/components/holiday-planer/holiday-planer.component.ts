import { Component, OnInit } from '@angular/core';
import {AllEmployees} from "../../models/dto/AllEmployees";
import {EmployeeService} from "../../services/employee.service"
import {HolidayRequest} from "../../models/dto/HolidayRequest";
import {Holiday} from "../../models/dto/Holiday";
import {HolidayType} from "../../models/dto/HolidayType";
import {delay} from "rxjs";


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


  getUserData():AllEmployees {
    let empList = new AllEmployees();
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

    let holidayType: HolidayType = new HolidayType()
    holidayType.typeName = "Urlaub";

    let holiday: Holiday = new Holiday();
    holiday.holidayType = holidayType;
    holiday.startDate = this.chossenStartDate;
    holiday.endDate = this.chossenEndDate;

    let holidayRequest: HolidayRequest = new HolidayRequest()
    holidayRequest.requestDate = date;
    holidayRequest.holidayRequestId = this.requestcounter+1;
    holidayRequest.holiday = holiday;
    if (empList.employees) { holidayRequest.requestingEmployeeId = empList.employees[0].employeeId;}
    holidayRequest.status = false;


    this.data.push(holidayRequest);


  }

  holidayaccepted()
  {
    this.statusDisplay = "accepted";
  }

  canceledaccepted()
  {
    this.statusDisplay = "canceled";
  }

  data:HolidayRequest[] = [

  ];

  displayedColumns = ['vocationStart', 'vocationEnd', 'days', 'statuss'];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
  }

}

