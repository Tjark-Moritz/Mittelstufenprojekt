import {Component, OnInit} from '@angular/core';
import {GetEmployee} from "../../models/dto/GetEmployee";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {ActivatedRoute, Router} from "@angular/router";
import {EmployeeService} from "../../services/employee.service";
import * as swal from "sweetalert2";
import {LoginComponent} from "../login/login.component";
import {Location} from "@angular/common";
import {BearerTokenService} from "../../services/bearer-token.service";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {LoginService} from "../../services/login.service";
import {Observable, Subject} from "rxjs";
import {UpdatedPassword} from "../../models/dto/UpdatedPassword";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  private para: any;
  public _SelectedEmployee: GetEmployee | undefined;

  public set selectedEmployee(val: GetEmployee | undefined) {
    this._SelectedEmployee = val;
    this.setViewRights();
  }

  public get selectedEmployee(): GetEmployee | undefined {
    return this._SelectedEmployee;
  }

  public originalSelectedEmployee: GetEmployee = new GetEmployee();

  public checkRequired: boolean = false;

  public pictureChanged: boolean = false;
  public set newProfilePicture(val : string) {
    if(this.selectedEmployee)
      this.selectedEmployee.base64ProfilePic = val;

    this.pictureChanged = true;
  }

  public newPassword: string = "";
  public newPasswordAgain: string = "";

  public _LoggedInUserView = false;
  public _AdminView = false;

  public get loggedInUserView(): boolean {
    return this._LoggedInUserView;
  }

  public get adminView(): boolean {
    return this._AdminView;
  }


  constructor(private domSanitizer: DomSanitizer, private route: ActivatedRoute, private location : Location, private empService: EmployeeService, private router: Router, private loginService: LoginService, private bearerTokenService: BearerTokenService) {
  }

  ngOnInit(): void {
    this.para = this.route.params.subscribe(params => {
      if(Object.keys(params).length == 0) {
        if (this.loginService.LoggedInUser) {
          this.selectedEmployee = JSON.parse(JSON.stringify(this.loginService.LoggedInUser)); // Json used to create a copy
          this.originalSelectedEmployee = this.loginService.LoggedInUser;
        }
      }
      else {
        this.empService.getEmployeeById(params['id']).subscribe(emp => {
           this.selectedEmployee = JSON.parse(JSON.stringify(emp)); // Json used to create a copy
           this.originalSelectedEmployee = emp;
        });
      }

      if(!this.selectedEmployee) {
        this.openFailedMessageBox("Profilseite von dem Benutzer konnte nicht gefunden werden!");
        this.location.back();
      }
    });
  }

  private setViewRights() {
    if(this.selectedEmployee) {
      this._LoggedInUserView = (this.selectedEmployee?.username == this.loginService.LoggedInUser.username);
    }
    this._AdminView = (this.bearerTokenService.getUserRole == UserRoleEnum.Admin);
  }

  public isProfilePictureSet(): boolean{
    if(this.selectedEmployee)
      return (this.selectedEmployee.base64ProfilePic != "");

    return false;
  }

  public getProfilePicture(): SafeResourceUrl | undefined{
    if(this.isProfilePictureSet() && this.selectedEmployee) {
      return this.domSanitizer.bypassSecurityTrustResourceUrl(this.selectedEmployee.base64ProfilePic);
    }

    return undefined;
  }


  public onClick_SaveDetails(){
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
        this.saveDetailsInDb();
      } else if (boxResult.isDenied) {
      }
    });
  }

  public onChange_OpenProfilePicture(event : any){
    if(event.target.files.length != 1)
      return;

    const selectedPicture : File = event.target.files[0];

    let reader = new FileReader();

    let here = this;
    reader.readAsDataURL(selectedPicture);
    reader.onload = function () {
      if(reader.result) {
        here.newProfilePicture = reader.result.toString();
      }
    };
    reader.onerror = function (error) {
      console.log('Error: ', error);
    };
  }

  public onClick_ChangeProfilePicture(){
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
        this.saveProfilePictureInDb();
      } else if (boxResult.isDenied) {
      }
    });
  }

  public onClick_RemoveProfilePicture(){
    this.newProfilePicture = "";
  }

  public onClick_SavePassword(){
    if(this.newPassword.length < 5){
      this.openFailedMessageBox("Neues Passwort muss mindestens 5 Zeichen haben!");
    }
    else if(this.newPassword != this.newPasswordAgain){
      this.openFailedMessageBox("Wiederholtes Passwort ist nicht identisch!");
    }
    else {
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

  private openFailedMessageBox(text: string){
    swal.default.fire({
      title: text,
      icon: "error",
      showCloseButton: true
    })
  }

  private openSavedMessageBox(){
    swal.default.fire({
      position: 'top',
      icon: 'success',
      title: 'Gespeichert!',
      showCloseButton: true,
      timer: 1500
    });
  }

  private saveProfilePictureInDb(){
    if(this.selectedEmployee) {
      let changes: Map<string, string> = new Map<string, string>();

      if (this.originalSelectedEmployee.base64ProfilePic != this.selectedEmployee.base64ProfilePic)
        changes.set("base64ProfilePic", this.selectedEmployee.base64ProfilePic);

      this.sendEmployeeChangesToDb(changes);
    }
  }

  private saveDetailsInDb(){
    if(this.selectedEmployee) {
      let changes: Map<string, string> = new Map<string, string>();

      if (this.originalSelectedEmployee.lastName != this.selectedEmployee.lastName)
        changes.set("lastName", this.selectedEmployee.lastName);

      if (this.originalSelectedEmployee.firstName != this.selectedEmployee.firstName)
        changes.set("firstName", this.selectedEmployee.firstName);

      if (this.originalSelectedEmployee.street != this.selectedEmployee.street)
        changes.set("street", this.selectedEmployee.street);

      if (this.originalSelectedEmployee.zipcode != this.selectedEmployee.zipcode)
        changes.set("zipcode", this.selectedEmployee.zipcode);

      if (this.originalSelectedEmployee.city != this.selectedEmployee.city)
        changes.set("city", this.selectedEmployee.city);

      if (this.originalSelectedEmployee.phone != this.selectedEmployee.phone)
        changes.set("phone", this.selectedEmployee.phone);

      if (this.originalSelectedEmployee.email != this.selectedEmployee.email)
        changes.set("email", this.selectedEmployee.email);

      this.sendEmployeeChangesToDb(changes);
    }
  }

  private sendEmployeeChangesToDb(changes: Map<string, string>){
    if(this.selectedEmployee) {
      if (changes.size == 0)
        return;

      if (this.selectedEmployee.id) {
        this.empService.updateEmployee(changes, this.selectedEmployee.id);
        this.openSavedMessageBox();
      } else {
        this.openFailedMessageBox("Mitarbeiternummer ist nicht definiert!");
      }
    }
  }

  private savePasswordInDb(skipSuccessBox: boolean = false){
    if(this._LoggedInUserView) {
      let updatedPassword = new UpdatedPassword(this.newPassword, this.newPasswordAgain);
      this.empService.updateEmployeePassword(updatedPassword);
    }

    // Todo: Passwort ändern bei admin view
    if(this._AdminView && !this._LoggedInUserView){

    }

    if(!skipSuccessBox){
      this.openSavedMessageBox();
    }
  }
}
