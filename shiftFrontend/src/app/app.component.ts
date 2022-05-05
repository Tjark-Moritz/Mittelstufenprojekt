import {Component} from '@angular/core';
import {UserCookieService} from "./services/user-cookie.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private userCookieService: UserCookieService) {

  }
}
