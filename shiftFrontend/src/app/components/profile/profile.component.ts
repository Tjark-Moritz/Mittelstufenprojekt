import { Component, OnInit } from '@angular/core';
import {GetEmployee} from "../../models/dto/GetEmployee";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {ActivatedRoute} from "@angular/router";
import {EmployeeService} from "../../services/employee.service";
import * as swal from "sweetalert2";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  private para: any;
  private selectedEmployee: GetEmployee | undefined;
  private profilePictureBase64 : string = "";

  public newPassword: string = "";

  public loggedInUserView = false;
  public adminView = true;

  constructor(private domSanitizer: DomSanitizer, private route: ActivatedRoute, private empService: EmployeeService) {
    this.domSanitizer.bypassSecurityTrustResourceUrl(this.profilePictureBase64);
  }

  ngOnInit(): void {
    this.para = this.route.params.subscribe(params => {
      this.empService.getEmployeeById(params['id']).subscribe(emp => {
         this.selectedEmployee = emp;
      });
    });
  }

  public isProfilePictureSet(): boolean{
    if(this.profilePictureBase64 != "")
      return true;
    else
      return false;
  }

  public getProfilePicture(): SafeResourceUrl | undefined{
    if(this.isProfilePictureSet()) {
      return this.domSanitizer.bypassSecurityTrustResourceUrl(this.profilePictureBase64);
    }

    return undefined;
  }


  public onClick_SaveDetails(){

  }

  public onClick_ChangeProfilePicture(){

  }

  public onClick_RemoveProfilePicture(){

  }

  public onClick_SavePassword(){
    swal.default.fire({
      title: 'Änderungen wirklich speichern?',
      showDenyButton: true,
      confirmButtonText: 'Ja',
      denyButtonText: 'Nein',
      customClass: {
        actions: 'my-actions',
        confirmButton: 'order-1',
        denyButton: 'order-2',
      }
    }).then((boxResult) => {
      if (boxResult.isConfirmed) {
        this.savePasswordInDb();
      } else if (boxResult.isDenied) {
      }
    });
  }

  public onClick_ResetPassword(){
    swal.default.fire({
      title: 'Passwort wirklich zurücksetzen?',
      showDenyButton: true,
      confirmButtonText: 'Ja',
      denyButtonText: 'Nein',
      customClass: {
        actions: 'my-actions',
        confirmButton: 'order-1',
        denyButton: 'order-2',
      }
    }).then((boxResult) => {
      if (boxResult.isConfirmed) {
        let rdmText: string = "";
        let rdmLength: number = 8;
        let possible: string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (let i: number = 0; i < rdmLength; i++) {
          rdmText += possible.charAt(Math.floor(Math.random() * possible.length));
        }

        this.newPassword = rdmText;
        this.savePasswordInDb(true);
      } else if (boxResult.isDenied) {
      }
    });
  }

  private openSavedMessageBox(){
    swal.default.fire({
      position: 'top',
      icon: 'success',
      title: 'Änderungen gespeichert!',
      showCloseButton: true,
      timer: 2500
    });
  }

  private savePasswordInDb(skipSuccessBox: boolean = false){
    console.info("Neues Passwort wurde gespeichert: '" + this.newPassword + "'");
    if(!skipSuccessBox){
      this.openSavedMessageBox();
    }
  }
}
