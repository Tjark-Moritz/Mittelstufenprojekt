import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BearerTokenService} from "./bearer-token.service";
import {Observable} from "rxjs";
import {AddShiftType} from "../models/dto/AddShiftType";
import {GetShiftType} from "../models/dto/GetShiftType";

@Injectable({
  providedIn: 'root'
})
export class ShiftTypeService {
  private urlPre = "/shifttype"

  constructor(private httpClient: HttpClient, private bearerTokenService: BearerTokenService) {

  }

  getAllShiftTypes(): Observable<GetShiftType[]>{
    return this.httpClient.get<GetShiftType[]>(this.urlPre, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    });
  }

  deleteShiftType(shiftTypeId: number){
    this.httpClient.delete(this.urlPre + `/${shiftTypeId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  addShiftType(shiftType: AddShiftType, departmentId: number){
    this.httpClient.post(this.urlPre + `/department/${departmentId}`, shiftType,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }

  updateShiftType(shiftTypeMap: Map<string, object>, shiftTypeId: number){
    this.httpClient.patch(this.urlPre + `/${shiftTypeId}`, shiftTypeMap,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken.access_token}`)
    }).toPromise();
  }
}
