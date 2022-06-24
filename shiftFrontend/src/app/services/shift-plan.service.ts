import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {BearerTokenService} from "./bearer-token.service";
import {GetShiftPlan} from "../models/dto/GetShiftPlan";
import {AddShiftPlan} from "../models/dto/AddShiftPlan";
import {GetEmployee} from "../models/dto/GetEmployee";

@Injectable({
  providedIn: 'root'
})
export class ShiftPlanService {
  private urlPre = "/shiftplan"

  constructor(private httpClient: HttpClient, private bearerTokenService: BearerTokenService) {

  }

  getShiftplanById(shiftPlanId: number): Observable<GetShiftPlan>{
    return this.httpClient.get<GetShiftPlan>(this.urlPre + `/${shiftPlanId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken}`)
    });
  }

  getShiftplansByDeptId(deptId: number): Observable<GetShiftPlan[]>{
    return this.httpClient.get<GetShiftPlan[]>(this.urlPre + `/department/${deptId}`, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken}`)
    });
  }

  addShiftplan(shiftPlan: AddShiftPlan){
    this.httpClient.post(this.urlPre, shiftPlan,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken}`)
    }).toPromise();

    console.log("added Shiftplan");
  }

  deleteShiftplanById(shiftPlanId: number){
    this.httpClient.delete(this.urlPre + `/${shiftPlanId}`,{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearerTokenService.bearerToken}`)
    }).toPromise();

    console.log("deleted Employee");
  }
}
