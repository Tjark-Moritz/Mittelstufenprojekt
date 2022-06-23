import { Component, OnInit } from '@angular/core';
import {EmployeeService} from "../../services/employee.service";
import {GetEmployee} from "../../models/dto/GetEmployee";
//import {AllEmployees} from "../../models/dto/AllEmployees";
//import {HolidayType} from "../../models/dto/HolidayType";
//import {Holiday} from "../../models/dto/Holiday";
//import {HolidayRequest} from "../../models/dto/HolidayRequest";
//import {ShiftTradeRequest} from "../../models/dto/ShiftTradeRequest";
//import {AllShiftTrades} from "../../models/dto/AllShiftTrades";

@Component({
  selector: 'app-request-planner',
  templateUrl: './request-planner.component.html',
  styleUrls: ['./request-planner.component.css']
})
export class RequestPlannerComponent implements OnInit {

  chossenDate: Date = new Date();
  requestCounter: number = 0;
/*
  getUserData():AllEmployees {
    let empList = new AllEmployees();
    this.employeeService.getAllEmployees().subscribe(val => empList = val)
    return empList;
  }
*/
  closePicker(eventData:any, datepicker?:any)
  {
    datepicker.close();
    this.chossenDate = eventData.value._d;
  }
/*
  getRequestData():AllShiftTrades {
    let shiftList = new AllShiftTrades();
    this.shiftService.getAllShifts().subscribe(val => shiftList = val)
    return shiftList;
  }
*/
  sendRequest(){
   // let empList = this.getUserData();

    let date: Date = new Date();
/*
    let shiftTradeRequest: ShiftTradeRequest = new ShiftTradeRequest();


    shiftTradeRequest.oldShiftId = 1;
    shiftTradeRequest.newShiftId = 2;
    shiftTradeRequest.requestingEmployee = empList.employees?.[1];
    shiftTradeRequest.shiftTradeId = this.requestCounter+1;
    shiftTradeRequest.replyingEmployee = empList.employees?.[0];

    this.requestData.push(shiftTradeRequest);

*/
  }

  //requestData: ShiftTradeRequest [] = [];

  data = [
    {swapDate: 1, currentShift: 'Rajesh', requestedShift: 'rajesh@gmail.com', requester: 'aa',},
    {swapDate:2, currentShift: 'Paresh', requestedShift: 'paresh@gmail.com', requester: 'aa'},
    {swapDate:3, currentShift: 'Naresh', requestedShift: 'naresh@gmail.com', requester: 'aa'},
    {swapDate:4, currentShift: 'Suresh', requestedShift: 'suresh@gmail.com', requester: 'aa'},
    {swapDate:5, currentShift: 'Karan', requestedShift: 'karan@gmail.com', requester: 'aa'},
  ];

  displayedColumns = ['swapDate', 'currentShift', 'requestedShift', 'requester', 'checkBox'];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
  }

}


