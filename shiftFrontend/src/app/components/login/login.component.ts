import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BearerTokenService} from "../../services/bearer-token.service";
import {Router} from "@angular/router";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  private autoLoginChecked: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private loginService: LoginService) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if(this.loginService.isUserLoggedIn())
      this.router.navigate(["index"]);
  }

  get form() {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    if(this.loginForm.invalid) {
      return;
    }

    this.loginService.Login(this.form['username'].value, this.form['password'].value, this.autoLoginChecked);
  }

  onAutoLoginCheck(event:any) {
    this.autoLoginChecked = event.target.checked;
  }
}
