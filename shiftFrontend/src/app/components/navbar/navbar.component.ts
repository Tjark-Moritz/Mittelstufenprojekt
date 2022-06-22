import { Component, OnInit } from '@angular/core';
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {BearerTokenService} from "../../services/bearer-token.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  classApplied: boolean = false;
  public notificationAvailable : boolean = false;
  public static profilePictureBase64 : string = "";   // Todo: implement database profile getter

  constructor(private domSanitizer: DomSanitizer) {
    this.domSanitizer.bypassSecurityTrustResourceUrl(NavbarComponent.profilePictureBase64)
  }

  public isProfilePictureSet(): boolean{
    if(NavbarComponent.profilePictureBase64 != "")
      return true;
    else
      return false;
  }

  public getProfilePicture(): SafeResourceUrl | undefined{
    if(this.isProfilePictureSet()) {
      return this.domSanitizer.bypassSecurityTrustResourceUrl(NavbarComponent.profilePictureBase64);
    }

    return undefined;
  }

  ngOnInit(): void {
  }

  sideNavIsOpen(val: boolean) {
    this.classApplied = val;
  }

  public userlogout(){

  }
}
