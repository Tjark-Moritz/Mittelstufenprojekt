import { Injectable } from '@angular/core';
import * as swal from 'sweetalert2';
import {GetEmployee} from "../models/dto/GetEmployee";
import {BearerTokenService} from "./bearer-token.service";
import {UserCookieService} from "./user-cookie.service";
import {EmployeeService} from "./employee.service";
import {Router} from "@angular/router";
import jwtDecode from "jwt-decode";
import {NavbarComponent} from "../components/navbar/navbar.component";
import {GetShiftType} from "../models/dto/GetShiftType";
import {interval, Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private _LoggedInUser: GetEmployee | undefined;
  public get LoggedInUser(): GetEmployee {
    if(!this._LoggedInUser) {
      this.setLoggedInUser();
    }

    if(this._LoggedInUser)
      return this._LoggedInUser;
    else
      return new GetEmployee();
  }

  private waitForLoggedInUser(): GetEmployee {
    if(this._LoggedInUser == undefined) {
      console.log("1");
      setTimeout(this.waitForLoggedInUser, 250);
    }

    console.log(this._LoggedInUser)
    return this._LoggedInUser!;
  }


  constructor(private bearerTokenService: BearerTokenService, private employeeService: EmployeeService, private router: Router) {

  }

  public Login(username: string, password: string, autoLogin: boolean){
    this.bearerTokenService.generateBearerToken(username, password).subscribe(
      res => {
        this.bearerTokenService.bearerToken = res;
        this.setLoggedInUser();

        if(autoLogin) {
          UserCookieService.setBearerToken(res);
        }
        this.router.navigate(['/index']);
        this.startintervalLoggedInUserUpdate();
      }, () => {
        swal.default.fire({
          position: 'top',
          icon: 'error',
          title: 'Benutzername oder Passwort is fehlerhaft! Bitte überprüfen Sie die Eingaben und probieren sie es erneut!',
          showCloseButton: true,
          timer: 2500
        });
    });
  }

  public Logout() {
    this.bearerTokenService.resetBearerToken();
    UserCookieService.removeBearerToken();
    this.setLoggedInUser();

    this.router.navigate(['/login']);
  }

  public isUserLoggedIn(): boolean{
    this.autoLogin();

    return (this.bearerTokenService.isBearerTokenSet());
  }

  private autoLogin(){
    if(UserCookieService.isBearerTokenSet() && !this.bearerTokenService.isBearerTokenSet()) {
      this.bearerTokenService.bearerToken = UserCookieService.getBearerToken();
      this.setLoggedInUser();
    }
  }

  public updateLoggedInUser(emp: GetEmployee){
    this._LoggedInUser = emp;

    if (this._LoggedInUser && this._LoggedInUser.base64ProfilePic != null) {
      NavbarComponent.profilePictureBase64 = this._LoggedInUser.base64ProfilePic;
    }
  }

  private startintervalLoggedInUserUpdate() {
    let seconds: number = 5;
    setInterval(() => {
      this.setLoggedInUser();
    }, seconds * 1000);
  }

  private setLoggedInUser(){
    if (this.bearerTokenService.bearerToken.access_token == null) {
      this._LoggedInUser = undefined;
      NavbarComponent.profilePictureBase64 = "";
      return;
    }

    // @ts-ignore
    let username: string = jwtDecode(this.bearerTokenService.bearerToken.access_token).preferred_username;

    this.employeeService.getEmployeeByUsername(username).subscribe(x => {
      this.updateLoggedInUser(x);
    }, () => {
      console.error("Logged in user could not be found!");
    });
  }
}
