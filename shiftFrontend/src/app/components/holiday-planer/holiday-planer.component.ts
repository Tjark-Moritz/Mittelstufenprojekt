import { Component, OnInit , ChangeDetectorRef } from '@angular/core';
import {GetHoliday} from "../../models/dto/GetHoliday";
import {AddHoliday} from "../../models/dto/AddHoliday";
import {BearerTokenService} from "../../services/bearer-token.service";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {LoginService} from "../../services/login.service";
import { MatTableDataSource } from '@angular/material/table';
import {HolidayService} from "../../services/holiday.service";
import {GetDepartment} from "../../models/dto/GetDepartment";


@Component({
  selector: 'app-holiday-planer',
  templateUrl: './holiday-planer.component.html',
  styleUrls: ['./holiday-planer.component.css']
})

export class HolidayPlanerComponent implements OnInit
{

  tempHiolidayId: number = 0;

  display: string = "shiftuser";
  statusDisplay: string ="normal";

  chossenStartDate: Date = new Date();
  chossenEndDate: Date = new Date();

  dataSource = new MatTableDataSource<AddHoliday[]>();
  allHolidays: AddHoliday[] = [];
  activehol: GetHoliday = new GetHoliday();

  displayedColumns = ['vocationStart', 'vocationEnd', 'days', 'statuss'];

  constructor(private loginService: LoginService,
              private bearerTokenService: BearerTokenService,
              private holidayService: HolidayService)
  {

    let roleName: UserRoleEnum | undefined;
    // @ts-ignore
    roleName = this.bearerTokenService.getUserRole;
    if(roleName)
    {
      if(roleName == UserRoleEnum.Admin)
      {
        this.switchUserToAdmin();
      }
      else
      {
        this.switchUser();
      }
    }
    this.refresh();
  }



  ngOnInit(): void
  {
    this.refresh();
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
    let xDate: Date = new Date(eventData);


    //this.chossenStartDate = xDate.toDateString();
    this.chossenStartDate = xDate;
    datepicker.close();
  }

  closeEndPicker(eventData:any, datepicker?:any)
  {
    let xDate: Date = new Date(eventData);


    //this.chossenStartDate = xDate.toDateString();
    this.chossenEndDate = xDate;
    //this.chossenEndDate = eventData.value._d;
    datepicker.close();
  }

  requestcounter:number = 0;


  value: any;


  restDays:number | undefined = 0;


  startRequest()
  {
    let addHoliday: AddHoliday = new AddHoliday();
    addHoliday.startDate =  this.chossenStartDate;
    addHoliday.endDate = this.chossenEndDate;
    addHoliday.id = this.requestcounter+1;
    addHoliday.holidayTypeId = 1;
    this.requestcounter = this.requestcounter+1;

    addHoliday.employeeId = this.loginService.LoggedInUser.id;

    this.holidayService.addHoliday(addHoliday);
    this.refresh();
  }

  holidayaccepted(holidayElem : AddHoliday)
  {
    this.holidayService.answerHoliday(holidayElem.id,"ACCEPTED")
    this.statusDisplay = "ACCEPTED";
  }

  holidaycanceled(holidayElem : AddHoliday)
  {
    this.holidayService.answerHoliday(holidayElem.id,"DENIED")
    this.statusDisplay = "DENIED";
  }

  refresh()
  {
    this.holidayService.getAllHolidays().subscribe(res =>
    {
      if(res.length != 0)
      {
        this.allHolidays = res;
        this.restDays = this.loginService.LoggedInUser.numHolidaysLeft;
      }
    });
  }

  calculateDate(element: GetHoliday)
  {
    const msInDay = 24 * 60 * 60 * 1000;

    return Math.round(Math.abs(Number(element.endDate) - Number(element.startDate)) / msInDay);
  }
}
