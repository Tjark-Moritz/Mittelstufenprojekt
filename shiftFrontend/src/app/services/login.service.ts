import { Injectable } from '@angular/core';
import * as swal from 'sweetalert2';
import {GetEmployee} from "../models/dto/GetEmployee";
import {BearerTokenService} from "./bearer-token.service";
import {UserCookieService} from "./user-cookie.service";
import {EmployeeService} from "./employee.service";
import {Router} from "@angular/router";
import jwtDecode from "jwt-decode";
import {NavbarComponent} from "../components/navbar/navbar.component";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private _LoggedInUser: GetEmployee | undefined;
  public get LoggedInUser(): GetEmployee {
    if(!this._LoggedInUser)
      this.setLoggedInUser();

    if(this._LoggedInUser)
      return this._LoggedInUser;
    else
      return new GetEmployee();
  }

  constructor(private bearerTokenService: BearerTokenService, private employeeService: EmployeeService, private router: Router) { }

  public Login(username: string, password: string, autoLogin: boolean){
    this.bearerTokenService.generateBearerToken(username, password).subscribe(
      res => {
        this.bearerTokenService.bearerToken = res;
        this.setLoggedInUser();

        if(autoLogin) {
          UserCookieService.setBearerToken(res);
        }
        this.router.navigate(['/index']);
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

  private setLoggedInUser(){
    if (this.bearerTokenService.bearerToken.access_token == null) {
      return;
    }

    // @ts-ignore
    let username: string = jwtDecode(this.bearerTokenService.bearerToken.access_token).preferred_username;
    this.employeeService.getEmployeeByUsername(username).subscribe(x => {
      this._LoggedInUser = x;
    }, () => {
      console.error("Logged in user could not be found!");
    });

    // Todo: Remove dummy data
    let testEmp: GetEmployee = new GetEmployee();
    testEmp.id = 1;
    testEmp.city = "cidddyyy";
    testEmp.zipcode = "1337";
    testEmp.street = "suppper streeet 420";
    testEmp.email = "bestemp@email.com";
    testEmp.phone = "0123456789";
    testEmp.firstName = "ColläerFN";
    testEmp.lastName = "SpaßßßLN";
    testEmp.username = "dErBeStEeMpEUW";
    testEmp.base64ProfilePic = "data:image/png;base64,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";
    this._LoggedInUser = testEmp;

    if(this._LoggedInUser) {
      if (this._LoggedInUser.base64ProfilePic != null) {
        NavbarComponent.profilePictureBase64 = this._LoggedInUser.base64ProfilePic;
      }
    }
  }
}
