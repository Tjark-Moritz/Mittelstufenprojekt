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
import {AddHoliday} from "../../models/dto/AddHoliday";
import {RequestAnswer} from "../../models/dto/RequestAnswer";

@Component({
  selector: 'app-request-planner',
  templateUrl: './request-planner.component.html',
  styleUrls: ['./request-planner.component.css']
})
//todo da Jarno noch nicht fertig ist mit seiner ansicht, evtl drop down menue erstellen mit Employees zum auswählen

export class RequestPlannerComponent implements OnInit {

  allRequest: AddShiftTradeRequest[] = [];
  chossenDate: Date = new Date();
  requestCounter: number = 0;

  constructor(private employeeService: EmployeeService,
              private bearerTokenService: BearerTokenService,
              private loginService: LoginService,
              private shiftTradeRequestService: ShiftTradeRequestService)
  {
    /*
    let roleName: UserRoleEnum | undefined;
    // @ts-ignore
    roleName = this.bearerTokenService.getUserRole;
    if(roleName)
    {
      if(roleName == UserRoleEnum.Admin)
      {
        //this.switchUserToAdmin();
      }
      else
      {
        //this.switchUser();
      }
    }
    */

    //todo replying id raufinden, oldShiftid und new shift id
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
    //todo wie ziehe ich mir die
    //addRequest.oldShiftId =
    //todo wie ziehe ich mir die
    //addRequest.replyingEmployeeId =
    this.shiftTradeRequestService.AddTradeRequest(addRequest);
    this.refresh();
    /*
       // let empList = this.getUserData();

       // let date: Date = new Date();

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
    this.shiftTradeRequestService.AddTradeRequestStatus(addRequestupdate,1);
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
      console.log(res);
    });
  }
}


