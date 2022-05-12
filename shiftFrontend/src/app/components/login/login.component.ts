import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BearerTokenService} from "../../services/bearer-token.service";
import {UserCookieService} from "../../services/user-cookie.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  wrongLogin: boolean = false;
  private autoLoginChecked: boolean = false;

  constructor(private formBuilder: FormBuilder, private bearerTokenService: BearerTokenService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if(BearerTokenService.isLoggedIn)
      this.router.navigate(["index"]);
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

        this.router.navigate(['/index']);
      }, () => {
        this.wrongLogin = true;
      })
  }

  onAutoLoginCheck(event:any) {
    this.autoLoginChecked = event.target.checked;
  }

}
