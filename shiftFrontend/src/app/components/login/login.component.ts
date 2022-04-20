import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BearerTokenService} from "../../services/bearer-token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
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
      }, error => {
        window.alert("Wrong credentials");
      })
  }

}
