import {Component, OnInit } from '@angular/core';
import {EmployeeService} from "../../services/employee.service";
import {GetEmployee} from "../../models/dto/GetEmployee";
import {UserRoleEnum} from "../../models/UserRoleEnum";
//import {AllEmployees} from "../../models/dto/AllEmployees";
//import {HolidayType} from "../../models/dto/HolidayType";
//import {Holiday} from "../../models/dto/Holiday";
//import {HolidayRequest} from "../../models/dto/HolidayRequest";
//import {ShiftTradeRequest} from "../../models/dto/ShiftTradeRequest";
//import {AllShiftTrades} from "../../models/dto/AllShiftTrades";
import {BearerTokenService} from "../../services/bearer-token.service";
import {AddShiftTradeRequest} from "../../models/dto/AddShiftTradeRequest";
import {LoginService} from "../../services/login.service";
import {ShiftTradeRequestService} from "../../services/shift-trade-request.service";
import {RequestAnswer} from "../../models/dto/RequestAnswer";
import {DayDetailsComponent} from "../day-details/day-details.component";
import {GetShiftTradeRequest} from "../../models/dto/GetShiftTradeRequest";
import {ShiftPlan} from "../../models/dto/ShiftPlan";
import {GetShift} from "../../models/dto/GetShift";
import {GetShiftType} from "../../models/dto/GetShiftType";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-request-planner',
  templateUrl: './request-planner.component.html',
  styleUrls: ['./request-planner.component.css']
})
//todo evtl drop down menue erstellen mit Employees zum auswählen
//todo replying id raufinden, oldShiftid und new shift id übergeben aus tabelle
export class RequestPlannerComponent implements OnInit {

  allRequest: AddShiftTradeRequest[] = [];
  chossenDate: Date = new Date();
  requestCounter: number = 0;

  constructor(private employeeService: EmployeeService,
              private bearerTokenService: BearerTokenService,
              private loginService: LoginService,
              private dialog: MatDialog,
              private shiftTradeRequestService: ShiftTradeRequestService,
              )
  //private getShiftTradeRequest: GetShiftTradeRequest
  {
    //todo delete
    let requestArray: AddShiftTradeRequest[] = [];
    let request1:AddShiftTradeRequest = new AddShiftTradeRequest(this.loginService.LoggedInUser.id,2,3);
    let request2:AddShiftTradeRequest = new AddShiftTradeRequest(this.loginService.LoggedInUser.id,4,5,6);
    let request3:AddShiftTradeRequest = new AddShiftTradeRequest(this.loginService.LoggedInUser.id,7,8,9);
    //let request4:AddShiftTradeRequest = new AddShiftTradeRequest();
    requestArray.push(request1);
    requestArray.push(request2);
    requestArray.push(request3);
    this.allRequest = requestArray;

    this.refresh()
  }

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

    let addRequest: AddShiftTradeRequest = new AddShiftTradeRequest();
    addRequest.requestingEmployeeId = this.loginService.LoggedInUser.id;
    addRequest.newShiftId = this.requestCounter;
    this.requestCounter = this.requestCounter+1;
    //todo ist das richtig ?
    //addRequest.oldShiftId = this.getShiftTradeRequest.oldShift?.id;
    //todo ist das richtig ?
    //addRequest.replyingEmployeeId = this.getShiftTradeRequest.replyingEmployee?.id;
    this.shiftTradeRequestService.AddTradeRequest(addRequest);
    this.refresh();
  }


  //requestData: ShiftTradeRequest [] = [];

  displayedColumns = ['swapDate', 'currentShift', 'requestedShift', 'requester', 'checkBox'];

  ngOnInit(): void {
  }

  //todo muss noch RequestID angepasst werden
  okButton()
  {
    let addRequestupdate = new RequestAnswer();
    addRequestupdate.accepted = true;
    this.shiftTradeRequestService.AddTradeRequestStatus(addRequestupdate,1);
  }

  //todo muss noch RequestID angepasst werden
  declinedButton()
  {
    let addRequestupdate = new RequestAnswer();
    addRequestupdate.accepted = false;
    //todo delte AddTradeRequestStatus(shiftTypeMap: Map<string, object>, requestid: number){
    this.shiftTradeRequestService.AddTradeRequestStatus(addRequestupdate,1);
  }
  /*
    openDialog()
    {
      this.dialog.open(DayDetailsComponent,
      {
        position:
          {
            top: "0",
            right: "0",
          },
        height: "100vh",
        direction: "rtl",
        data:
          {}
      });
    }
  */
    openDialog(shiftPlan: ShiftPlan, date: Date) {
      if(shiftPlan.shifts){
        //let formattedDate: string = this.getFormattedDate(date);
        let shifts: GetShift[] = this.getShifts(shiftPlan, date)

        this.dialog.open(DayDetailsComponent, {
          position: {
            top: "0",
            right: "0",
          },
          height: "100vh",
          direction: "rtl",
          data: {
            date,
            //formattedDate,
            shifts,
          }
        });
      }
    }

  /*
  getFormattedDate(date: Date): string {
    return this.weekdays[date.getDay()] + ", " + date.getDate() + "." + this.months[date.getMonth()] + " " + date.getFullYear();
  }

   */


  getShifts(shiftPlan: ShiftPlan, date?: Date, shiftType?: GetShiftType): GetShift[] {
    if(shiftPlan.shifts) {
      if (date) {
        let chosenDayShifts: GetShift[];
        chosenDayShifts = shiftPlan.shifts.filter(shift => shift.shiftDate?.toDateString() == date.toDateString())

        if (shiftType) {
          return chosenDayShifts.filter(shift => shift.shiftType?.id == shiftType.id)
        }
        return chosenDayShifts
      }
      return shiftPlan.shifts
    }
    return []
  }




  refresh()
  {
    this.shiftTradeRequestService.getAllRequestRequestor().subscribe(res =>
    {
      if(res.length != 0)
      {
        //todo
        //this.allRequest = res;
      }
    });
  }
}


