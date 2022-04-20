import { Injectable } from '@angular/core';
import {BearerToken} from "../models/bearerToken";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BearerTokenService {
  private static staticBearerToken: BearerToken;
  private authUrl = '/auth';

  constructor(private httpClient: HttpClient){
  }

  generateBearerToken(username: string, password: string): Observable<BearerToken>{
    let body = new URLSearchParams();
    body.set('grant_type', 'password');
    body.set('client_id', 'shiftplanner');
    body.set('username', username);
    body.set('password', password);

    return this.httpClient.post<BearerToken>(this.authUrl,body.toString(),{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
    })
  }

  set bearerToken(token) {
    BearerTokenService.staticBearerToken = token;
  }

  get bearerToken(): BearerToken {
    return BearerTokenService.staticBearerToken;
  }
}
