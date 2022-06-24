import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {BearerTokenService} from "./bearer-token.service";
import {GetShiftTradeRequest} from "../models/dto/GetShiftTradeRequest";
import {AddShiftTradeRequest} from "../models/dto/AddShiftTradeRequest";
import {LoginService} from "./login.service";
import {GetEmployee} from "../models/dto/GetEmployee";
import {RequestAnswer} from "../models/dto/RequestAnswer";


@Injectable({
  providedIn: 'root'
})
export class ShiftTradeRequestService {
  private urlPre = "/request/shifttrade"

  constructor(private httpClient: HttpClient,
              private bearerTokenService: BearerTokenService,
              private loginService: LoginService)
  {

  }

  getAllRequestRespondent(): Observable<GetShiftTradeRequest[]> {
    return this.httpClient.get<GetShiftTradeRequest[]>(this.urlPre + `/requestor/${this.loginService.LoggedInUser.id}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  getAllRequestRequestor(): Observable<GetShiftTradeRequest[]> {
    return this.httpClient.get<GetShiftTradeRequest[]>(this.urlPre + `/respondent/${this.loginService.LoggedInUser.id}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  AddTradeRequest(request: AddShiftTradeRequest){
    this.httpClient.post(this.urlPre, request,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
    console.log("added Shift Trade Request");
  }

  AddTradeRequestStatus(requestAnswer: RequestAnswer, requestid: number){
    this.httpClient.post(this.urlPre + `/${requestid}`, requestAnswer,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
    console.log("added Shift Trade Request Status");
  }
}

