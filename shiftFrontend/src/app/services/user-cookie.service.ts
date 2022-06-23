import { Injectable } from '@angular/core';
import { CookieService} from "ngx-cookie-service";
import { BearerToken } from "../models/bearerToken";
import jwtDecode from "jwt-decode";

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
    if (bearerToken.access_token != null) {
      let decodedToken = jwtDecode(bearerToken.access_token);
      // @ts-ignore
      // Ignore needed because there is a error detected that isn't actually there.
      let expDate = new Date(decodedToken.exp * 1000);
      UserCookieService.cookieService.set(UserCookieService.bearerTokenCookieName, JSON.stringify(bearerToken), expDate);
    }
  }

  public static getBearerToken() : BearerToken {
    return <BearerToken>JSON.parse(UserCookieService.cookieService.get(UserCookieService.bearerTokenCookieName));
  }

  public static removeBearerToken() {
    UserCookieService.cookieService.deleteAll(UserCookieService.bearerTokenCookieName);
  }
}
