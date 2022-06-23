import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {BearerTokenService} from "./bearer-token.service";
import {GetHoliday} from "../models/dto/GetHoliday";
import {AddHoliday} from "../models/dto/AddHoliday";


@Injectable({
  providedIn: 'root'
})
export class HolidayService {
  private urlPre = "/requests/holiday"

  constructor(private httpClient: HttpClient, private bearerTokenService: BearerTokenService)
  {

  }

  getAllHolidays(): Observable<GetHoliday[]> {
    return this.httpClient.get<GetHoliday[]>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  addHoliday(holiday: AddHoliday){
    this.httpClient.post(this.urlPre, holiday,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
    console.log("added Holiday");
  }

  deleteHoliday(holidayid: number){
    this.httpClient.delete(this.urlPre + `/${holidayid}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
    console.log("deleted Holiday");
  }
}
