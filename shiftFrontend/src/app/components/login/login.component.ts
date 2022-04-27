import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BearerTokenService} from "../../services/bearer-token.service";
import {UserCookieService} from "../../services/user-cookie.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  wrongLogin: boolean = false;
  private autoLoginChecked: boolean = false;

  constructor(private formBuilder: FormBuilder, private bearerTokenService: BearerTokenService) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
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
        this.bearerTokenService.bearerToken = res;

        if(this.autoLoginChecked) {
          UserCookieService.setBearerToken(res);
        }

        this.wrongLogin = false;

        // Todo: Redirect on successfull login (LF12-54)
      }, error => {
        this.wrongLogin = true;
      })
  }

  onAutoLoginCheck(event:any) {
    this.autoLoginChecked = event.target.checked;
  }

}
