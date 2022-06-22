import { Component, OnInit , ChangeDetectorRef } from '@angular/core';
import {GetEmployee} from "../../models/dto/GetEmployee";
import {GetHoliday} from "../../models/dto/GetHoliday";
import {AddHoliday} from "../../models/dto/AddHoliday";
//import {HolidayType} from "../../models/dto/HolidayType";
import {EmployeeService} from "../../services/employee.service";
import {BearerTokenService} from "../../services/bearer-token.service";
import jwtDecode from "jwt-decode";
import {LoginComponent} from "../login/login.component";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {LoginService} from "../../services/login.service";
import { MatTableDataSource } from '@angular/material/table';


@Component({
  selector: 'app-holiday-planer',
  templateUrl: './holiday-planer.component.html',
  styleUrls: ['./holiday-planer.component.css']
})

export class HolidayPlanerComponent implements OnInit
{

  display: string = "shiftuser";
  statusDisplay: string ="normal";

  chossenStartDate: Date = new Date();
  chossenEndDate: Date = new Date();

  // data:HolidayRequest[] = []; // Siehe unten
  //data:AddHoliday[] = [];
  data = new MatTableDataSource<AddHoliday[]>();

  displayedColumns = ['vocationStart', 'vocationEnd', 'days', 'statuss'];

  constructor(private employeeService: EmployeeService,
              private loginService: LoginService,
              private bearerTokenService: BearerTokenService,
              private changeDetectorRefs: ChangeDetectorRef)
  {
    let roleName: UserRoleEnum | undefined;
    // @ts-ignore
    roleName = this.bearerTokenService.getUserRole;
    if(roleName)
    {
      if(roleName == UserRoleEnum.Admin)
      {
        this.switchUserToAdmin()
      }
      else
      {
        this.switchUser();
      }
    }
  }

  ngOnInit(): void
  {
    this.refresh();
  }

  getUserData():GetEmployee[] {
    let empList: GetEmployee[] = [];
    this.employeeService.getAllEmployees().subscribe(val => empList = val)
    return empList;
  }

  switchUserToAdmin()
  {
    this.display = "shiftadmin";
  }

  switchUser()
  {
    this.display = "shiftuser"
  }

  closeStartPicker(eventData:any, datepicker?:any)
  {

    this.chossenStartDate = eventData.value._d;
    datepicker.close();
  }

  closeEndPicker(eventData:any, datepicker?:any)
  {
  this.chossenEndDate = eventData.value._d;
    datepicker.close();
  }

  requestcounter:number = 0;


  value: any;

  startRequest()
  {
    let empList = this.getUserData();


    let addHoliday: AddHoliday = new AddHoliday();
    addHoliday.startDate =  this.chossenStartDate;
    addHoliday.endDate = this.chossenEndDate;
    addHoliday.id = this.requestcounter+1;
    addHoliday.typeId = 1;
    this.requestcounter = this.requestcounter+1;
    //LoginComponent.GetLoggedInUser;

    if (empList[0])
    {
      addHoliday.employeeId = this.loginService.LoggedInUser.id;
    }

    //this.data.push(addHoliday)
    this.refresh();
  }
/*

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
*/

  holidayaccepted()
  {
    this.statusDisplay = "ACCEPTED";
  }

  holidaycanceled()
  {
    this.statusDisplay = "DENIED";
  }

  refresh()
  {
    this.data = this.data;
    this.changeDetectorRefs.detectChanges();
  }
}

