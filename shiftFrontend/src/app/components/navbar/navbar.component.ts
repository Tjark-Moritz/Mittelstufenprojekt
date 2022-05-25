import { Component, OnInit } from '@angular/core';
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  public notificationAvailable : boolean = false;
  public safeProfilePicture: SafeResourceUrl;
  public profilePictureBase64 : string = "";   // Todo: implement database profile getter

  constructor(private domSanitizer: DomSanitizer) {
    this.safeProfilePicture = this.domSanitizer.bypassSecurityTrustResourceUrl(this.profilePictureBase64);
  }

  ngOnInit(): void {
  }

}
