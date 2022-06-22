import {Injectable} from '@angular/core';
import {BearerToken} from "../models/bearerToken";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserCookieService} from "./user-cookie.service";
import {UserRoleEnum} from "../models/UserRoleEnum";
import jwtDecode from "jwt-decode";
import {LoginService} from "./login.service";

@Injectable({
  providedIn: 'root'
})
export class BearerTokenService {
  private _BearerToken: BearerToken | undefined;
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

  public resetBearerToken(){
    this._BearerToken = undefined;
  }

  public set bearerToken(token: BearerToken) {
    this._BearerToken = token;

    //this.loginService.LoggedInUser;
  }

  public get bearerToken(): BearerToken {
    if(this._BearerToken)
      return this._BearerToken;
    else
      return new BearerToken();
  }

  public isBearerTokenSet(): boolean {
    return (this.bearerToken.access_token != undefined);
  }

  // returns undefined if the user isn't logged in
  public get getUserRole(): UserRoleEnum | undefined {
    if(!this.isBearerTokenSet() || this.bearerToken.access_token == null){
      return undefined;
    }

    let decodedToken = jwtDecode(this.bearerToken.access_token);
    // @ts-ignore
    let roleNames : string[] = decodedToken.realm_access.roles;
    console.info(roleNames)
    console.error(this.bearerToken.access_token)
    if(roleNames.includes(UserRoleEnum.Admin.toString())){
      return UserRoleEnum.Admin;
    }
    else if (roleNames.includes(UserRoleEnum.User.toString())){
      return UserRoleEnum.User;
    }

    return undefined;

  }
}
