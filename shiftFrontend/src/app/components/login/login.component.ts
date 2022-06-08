import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BearerTokenService} from "../../services/bearer-token.service";
import {UserCookieService} from "../../services/user-cookie.service";
import {Router} from "@angular/router";
import * as swal from 'sweetalert2';
import {GetEmployee} from "../../models/dto/GetEmployee";
import {EmployeeService} from "../../services/employee.service";
import {BearerToken} from "../../models/bearerToken";
import jwtDecode from "jwt-decode";
import {NavbarComponent} from "../navbar/navbar.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private static loggedInUser: GetEmployee;
  loginForm: FormGroup;
  wrongLogin: boolean = false;
  private autoLoginChecked: boolean = false;

  constructor(private formBuilder: FormBuilder, private bearerTokenService: BearerTokenService, private employeeService: EmployeeService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if(BearerTokenService.isLoggedIn)
      this.router.navigate(["index"]);
  }

  public static get GetLoggedInUser(): GetEmployee{
    return this.loggedInUser;
  }

  get form() {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    if(this.loginForm.invalid) {
      return;
    }

    this.bearerTokenService.generateBearerToken(this.form['username'].value, this.form['password'].value).subscribe(
      res => {
        BearerTokenService.bearerToken = res;

        if(this.autoLoginChecked) {
          UserCookieService.setBearerToken(res);
        }

        this.wrongLogin = false;
        this.setLoggedInUser(BearerTokenService.bearerToken);
        this.router.navigate(['/index']);
      }, () => {
        swal.default.fire({
          position: 'top',
          icon: 'error',
          title: 'Benutzername oder Passwort is fehlerhaft! Bitte überprüfen Sie die Eingaben und probieren sie es erneut!',
          showCloseButton: true,
          timer: 2500
        });

        this.wrongLogin = true;
      })
  }

  onAutoLoginCheck(event:any) {
    this.autoLoginChecked = event.target.checked;
  }

  private setLoggedInUser(token: BearerToken){
    if (token.access_token == null) {
      return;
    }

    // @ts-ignore
    let username: string = jwtDecode(token.access_token).preferred_username;
    this.employeeService.getEmployeeByUsername(username).subscribe(x => {
      LoginComponent.loggedInUser = x;
    });

    if(LoginComponent.loggedInUser) {
      if (LoginComponent.loggedInUser.base64ProfilePic != null) {
        NavbarComponent.profilePictureBase64 = LoginComponent.loggedInUser.base64ProfilePic;
      }
    }
  }

}
