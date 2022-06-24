import {Component, OnInit } from '@angular/core';
import {EmployeeService} from "../../services/employee.service";
import {GetEmployee} from "../../models/dto/GetEmployee";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {BearerTokenService} from "../../services/bearer-token.service";
import {AddShiftTradeRequest} from "../../models/dto/AddShiftTradeRequest";
import {LoginService} from "../../services/login.service";
import {ShiftTradeRequestService} from "../../services/shift-trade-request.service";
import {RequestAnswer} from "../../models/dto/RequestAnswer";
import {DayDetailsComponent} from "../day-details/day-details.component";
import {MatDialog} from "@angular/material/dialog";
import {GetShift} from "../../models/dto/GetShift";

@Component({
  selector: 'app-request-planner',
  templateUrl: './request-planner.component.html',
  styleUrls: ['./request-planner.component.css']
})
//todo replying id raufinden, oldShiftid und new shift id übergeben aus tabelle
export class RequestPlannerComponent implements OnInit {

  allRequest: AddShiftTradeRequest[] = [];
  chossenDate: Date = new Date();
  chossenDateLoggedinUser: Date = new Date();
  requestCounter: number = 0;
  personId: number = 0;

  constructor(private employeeService: EmployeeService,
              private bearerTokenService: BearerTokenService,
              private loginService: LoginService,
              private dialog: MatDialog,
              private shiftTradeRequestService: ShiftTradeRequestService,
              )
  //private
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

  loggedinUserDate(eventData:any, datepicker?:any)
  {
    datepicker.close();
    this.chossenDateLoggedinUser = eventData.value._d;
  }



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
  sendRequest()
  {
    //todo meine Shicht von this chosenLoginduserid
    let addRequest: AddShiftTradeRequest = new AddShiftTradeRequest();
    addRequest.requestingEmployeeId = this.loginService.LoggedInUser.id;
    addRequest.newShiftId = this.requestCounter;
    this.requestCounter = this.requestCounter+1;


    let getShift: GetShift = new GetShift()
    getShift.shiftDate = this.chossenDateLoggedinUser;
    //todo schicht aufrufen (die die person die ausgewählt wurde  an dem Tag hat und die old.Shift.id ziehen)
    // an dem tag wo meine einglocgter user ist
    addRequest.oldShiftId =
    //todo ist das richtig ?
    // dem tag wo ich die haben will
    addRequest.replyingEmployeeId = this.personId;
    this.shiftTradeRequestService.AddTradeRequest(addRequest);

    this.refresh();
  }

  displayedColumns = ['swapDate', 'currentShift', 'requestedShift', 'requester', 'checkBox'];

  ngOnInit(): void {
  }

  okButton()
  {
    let addRequestupdate = new RequestAnswer();
    addRequestupdate.accepted = true;
    this.requestCounter = this.requestCounter+1;
    //todo zaehlt er so immer einen hoch?
    this.shiftTradeRequestService.AddTradeRequestStatus(addRequestupdate,this.requestCounter);
  }

  declinedButton()
  {
    let addRequestupdate = new RequestAnswer();
    addRequestupdate.accepted = false;
    this.requestCounter = this.requestCounter+1;
    //todo zaehlt er so immer einen hoch? Frage lenni
    this.shiftTradeRequestService.AddTradeRequestStatus(addRequestupdate,this.requestCounter);
  }

  async chosenPorson(personName: string)
  {
    personName.split(" ")
    this.personId = await this.employeeService.getEmployeeIdByFirstAndLastName(personName[0],personName[1]);
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


