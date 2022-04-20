import { Injectable } from '@angular/core';
import { CookieService} from "ngx-cookie-service";
import { BearerToken } from "../models/bearerToken";
import { BearerTokenService } from "./bearer-token.service";

@Injectable({
  providedIn: 'root'
})
export class UserCookieService {
  private static bearerTokenCookieName: string = "BearerTokenCookie";
  private static cookieService: CookieService;

  constructor(private cookieService: CookieService) {
    UserCookieService.cookieService = cookieService;
  }

  public static isBearerTokenSet() : boolean{
    return UserCookieService.cookieService.check(UserCookieService.bearerTokenCookieName);
  }

  public static setBearerToken(bearerToken: BearerToken) {
    UserCookieService.cookieService.set(UserCookieService.bearerTokenCookieName, JSON.stringify(bearerToken), bearerToken.expires_in);
  }

  public static getBearerToken() : BearerToken {
    return <BearerToken>JSON.parse(UserCookieService.cookieService.get(UserCookieService.bearerTokenCookieName));
  }

  public static removeBearerToken() {
    UserCookieService.cookieService.deleteAll(UserCookieService.bearerTokenCookieName);
  }
}
