import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {BearerTokenService} from "./bearer-token.service";
import * as swal from 'sweetalert2';
import {LoginService} from "./login.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuardService implements CanActivate{

  constructor(private router: Router, private loginService: LoginService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    if(this.loginService.isUserLoggedIn()) {
      return true;
    }

    swal.default.fire({
      position: 'top',
      icon: 'warning',
      title: 'Bitte vorher einloggen!',
      timer: 1500
    });

    this.router.navigate(["login"]);
    return false;
  }
}
