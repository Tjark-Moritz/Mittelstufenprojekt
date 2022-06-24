import { Component, OnInit , ChangeDetectorRef } from '@angular/core';
import {GetHoliday} from "../../models/dto/GetHoliday";
import {AddHoliday} from "../../models/dto/AddHoliday";
import {BearerTokenService} from "../../services/bearer-token.service";
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

  dataSource = new MatTableDataSource<AddHoliday[]>();
  allHolidays: AddHoliday[] = [];

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


    //todo delete
    let hollidayArray: AddHoliday[] = [];
    let holiday1:AddHoliday = new AddHoliday(1,1,new Date("2022-06-25"),new Date("2022-06-29"),1);
    let holiday2:AddHoliday = new AddHoliday(2,1,new Date("2022-06-01"),new Date("2022-06-10"),1);
    let holiday3:AddHoliday = new AddHoliday(3,1,new Date("2022-07-10"),new Date("2022-07-20"),1);
    hollidayArray.push(holiday1);
    hollidayArray.push(holiday2);
    hollidayArray.push(holiday3);
    this.allHolidays = hollidayArray;

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


  restDays:number | undefined = 0;


  startRequest()
  {
    let addHoliday: AddHoliday = new AddHoliday();
    addHoliday.startDate =  this.chossenStartDate;
    addHoliday.endDate = this.chossenEndDate;
    addHoliday.id = this.requestcounter+1;
    addHoliday.typeId = 1;
    this.requestcounter = this.requestcounter+1;

    addHoliday.employeeId = this.loginService.LoggedInUser.id;

    this.holidayService.addHoliday(addHoliday);
    this.refresh();
  }

  //todo check senden des Statuses
  holidayaccepted(holidayElem : AddHoliday)
  {
    let holidaysGet: GetHoliday;
    console.info(holidayElem.id)


    this.statusDisplay = "ACCEPTED";
  }

  //todo check senden des Statuses
  holidaycanceled()
  {
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
      console.log(res);
    });
  }
}
