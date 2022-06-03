import {Injectable} from '@angular/core';
import {BearerToken} from "../models/bearerToken";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserCookieService} from "./user-cookie.service";
import {UserRoleEnum} from "../models/UserRoleEnum";
import jwtDecode from "jwt-decode";

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

  public static set bearerToken(token) {
    BearerTokenService.staticBearerToken = token;
  }

  public static get bearerToken(): BearerToken {
    if(UserCookieService.isBearerTokenSet() && !BearerTokenService.staticBearerToken)
      BearerTokenService.staticBearerToken = UserCookieService.getBearerToken();

    return BearerTokenService.staticBearerToken;
  }

  public static get isLoggedIn(): boolean {
    return !!BearerTokenService.bearerToken;
  }

  // returns undefined if the user isn't logged in
  public static get getUserRoles(): UserRoleEnum | undefined {
    if(BearerTokenService.bearerToken == null || BearerTokenService.bearerToken.access_token == null){
      return undefined;
    }

    let decodedToken = jwtDecode(BearerTokenService.bearerToken.access_token);
    // @ts-ignore
    let roleNames : string[] = decodedToken.realm_access.roles;
    if(roleNames.includes(UserRoleEnum.Admin.toString())){
      return UserRoleEnum.Admin;
    }
    else if (roleNames.includes(UserRoleEnum.User.toString())){
      return UserRoleEnum.User;
    }

    return undefined;

  }
}
