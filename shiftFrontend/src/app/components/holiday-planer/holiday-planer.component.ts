import { Component, OnInit , ChangeDetectorRef } from '@angular/core';
import {GetHoliday} from "../../models/dto/GetHoliday";
import {AddHoliday} from "../../models/dto/AddHoliday";
//import {HolidayType} from "../../models/dto/HolidayType";
import {BearerTokenService} from "../../services/bearer-token.service";
import jwtDecode from "jwt-decode";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {LoginService} from "../../services/login.service";
import { MatTableDataSource } from '@angular/material/table';
import {HolidayService} from "../../services/holiday.service";


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

  //data:AddHoliday[] = [];
  dataSource = new MatTableDataSource<AddHoliday[]>();
  allHolidays: AddHoliday[] = [];

  displayedColumns = ['vocationStart', 'vocationEnd', 'days', 'statuss'];

  constructor(private loginService: LoginService,
              private bearerTokenService: BearerTokenService,
              private holidayService: HolidayService,
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
    let addHoliday: AddHoliday = new AddHoliday();
    addHoliday.startDate =  this.chossenStartDate;
    addHoliday.endDate = this.chossenEndDate;
    addHoliday.id = this.requestcounter+1;
    addHoliday.typeId = 1;
    this.requestcounter = this.requestcounter+1;

    addHoliday.employeeId = this.loginService.LoggedInUser.id;

    console.log(addHoliday);
    console.log("nassss");
    this.holidayService.addHoliday(addHoliday);

    this.refresh();
  }

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
    this.holidayService.getAllHolidays().subscribe(res =>
    {
       console.log(res)
      this.allHolidays= res;
    });
  }
}

