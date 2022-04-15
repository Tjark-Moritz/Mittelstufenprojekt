import { Injectable } from '@angular/core';
import {BearerToken} from "../models/bearerToken";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserCookieService} from "./user-cookie.service";

@Injectable({
  providedIn: 'root'
})
export class BearerTokenService {
  private static staticBearerToken: BearerToken;
  private authUrl = '/auth';

  constructor(private httpClient: HttpClient){
  }

  generateBearerToken(username: string, password: string): Observable<BearerToken>{
    return this.httpClient.post<BearerToken>(this.authUrl,{},{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded'),
      params: new HttpParams()
        .set('grant_type', 'password')
        .set('client_id', 'shiftplanner')
        .set('username', username)
        .set('password', password)
    })
  }

  set bearerToken(token) {
    BearerTokenService.staticBearerToken = token;
    UserCookieService.setBearerToken(token);
  }

  get bearerToken(): BearerToken {
    return BearerTokenService.staticBearerToken;
  }
}
