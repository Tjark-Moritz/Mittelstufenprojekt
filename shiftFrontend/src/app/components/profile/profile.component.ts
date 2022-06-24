import {Component, OnInit} from '@angular/core';
import {GetEmployee} from "../../models/dto/GetEmployee";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {ActivatedRoute, Router} from "@angular/router";
import {EmployeeService} from "../../services/employee.service";
import * as swal from "sweetalert2";
import {Location} from "@angular/common";
import {BearerTokenService} from "../../services/bearer-token.service";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {LoginService} from "../../services/login.service";
import {UpdatedPassword} from "../../models/dto/UpdatedPassword";
import {GetShiftType} from "../../models/dto/GetShiftType";
import {DepartmentService} from "../../services/department.service";
import {NavbarComponent} from "../navbar/navbar.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  constructor(private domSanitizer: DomSanitizer, private route: ActivatedRoute, private location : Location,
              private empService: EmployeeService, private router: Router, private loginService: LoginService,
              private bearerTokenService: BearerTokenService, private departmentService: DepartmentService) {
  }

  ngOnInit(): void {
    this.para = this.route.params.subscribe(params => {
      if(Object.keys(params).length == 0) {
        this.loadLoggedInProfile();
      }
      else {
        this.loadProfile(params['id']);
      }

      console.log(params)
    });
  }



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

  private _SelectedShiftType: GetShiftType | undefined;
  public get selectedShiftType(): GetShiftType | undefined {
    return this._SelectedShiftType;
  }
  public set selectedShiftType(val){
    if(this.selectedEmployee)
      this.selectedEmployee.preferredShiftType = val;

    this._SelectedShiftType = val;
  }

  private _AllShiftTypes: GetShiftType[] = [];

  public set allShiftTypes(val){
    this.selectedShiftType = val.find(x => x.id == this.loginService.LoggedInUser.preferredShiftType?.id);

    this._AllShiftTypes = val;
  }

  public get allShiftTypes(): GetShiftType[] {
    return this._AllShiftTypes;
  };

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

  public onClick_SaveAllChanges() {
    swal.default.fire({
      title: 'Änderungen wirklich speichern?',
      icon: "question",
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
        this.saveAllChanges();
      } else if (boxResult.isDenied) {
      }
    });
  }

  private saveAllChanges() {
    if(this.selectedEmployee) {
      let empChanges: {[key: string]: object} = {};
      let detailsAreChanged: boolean = false;

      if (this.originalSelectedEmployee.lastName != this.selectedEmployee.lastName) {
        empChanges["lastName"] = new String(this.selectedEmployee.lastName);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.firstName != this.selectedEmployee.firstName) {
        empChanges["firstName"] = new String(this.selectedEmployee.firstName);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.street != this.selectedEmployee.street){
        empChanges["street"] = new String(this.selectedEmployee.street);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.zipcode != this.selectedEmployee.zipcode){
        empChanges["zipcode"] = new String(this.selectedEmployee.zipcode);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.city != this.selectedEmployee.city){
        empChanges["city"] = new String(this.selectedEmployee.city);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.phone != this.selectedEmployee.phone){
        empChanges["phone"] = new String(this.selectedEmployee.phone);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.email != this.selectedEmployee.email){
        empChanges["email"] = new String(this.selectedEmployee.email);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.base64ProfilePic != this.selectedEmployee.base64ProfilePic){
        empChanges["base64ProfilePic"] = new String(this.selectedEmployee.base64ProfilePic);
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.preferredShiftType != this.selectedEmployee.preferredShiftType && this.selectedShiftType && this.selectedShiftType.id){
        empChanges["preferredShiftType"] = new Number(this.selectedShiftType.id);
        detailsAreChanged = true;
      }

      console.log(empChanges)

      let detailsChangesResult: boolean = true;
      if(detailsAreChanged) {
        this.sendEmployeeChangesToDb(empChanges);
        if(!detailsChangesResult){
          this.openFailedMessageBox("Konnte nicht gespeichert werden!", "Mitarbeiternummer ist nicht definiert!<br/>Details wurden nicht gespeichert!");
        }
      }

      let passwordChangesResult: boolean = true;
      if((this.newPassword != "" && this.adminView && !this.loggedInUserView) || (this.newPassword != "" && this.newPasswordAgain != "" && this.loggedInUserView)){
        passwordChangesResult = this.sendEmployeePasswordChangesToDb();

        if(!passwordChangesResult)
        {
          this.openFailedMessageBox("Konnte nicht gespeichert werden!", "Passwort konnte nicht gespeichert werden!");
        }
      }

      if(detailsChangesResult && passwordChangesResult) {
        this.openSavedMessageBox();
      }
    }
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
        let pictureString = reader.result.toString();

        let maxPicLength: number = 1000000 - 5000;
        if(pictureString.length > maxPicLength){
          here.openFailedMessageBox("Konnte nicht gespeichert werden!", "Das ausgewählte Bild überschreitet leider die maximale Dateigröße!<br/>" +
            "Maximale Größe: " + (3 * (maxPicLength / 4)) + " bytes");
          return;
        }
        else {
          here.newProfilePicture = pictureString;
        }
      }
    };
    reader.onerror = function (error) {
      console.log('Error: ', error);
    };
  }

  public onClick_GeneratePassword(){
    let rdmText: string = "";
    let rdmLength: number = 8;
    let possible: string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (let i: number = 0; i < rdmLength; i++) {
      rdmText += possible.charAt(Math.floor(Math.random() * possible.length));
    }

    this.newPassword = rdmText;
  }

  public onClick_DeleteEmployee(){
    let title: string = "Mitarbeiter '" + this.originalSelectedEmployee.username + "' (ID: " + this.originalSelectedEmployee.id + ") wirklich löschen?";
    swal.default.fire({
      title: title,
      icon: "warning",
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
        this.deleteEmployee();
      } else if (boxResult.isDenied) {
      }
    });
  }

  public onClick_MakeAdmin() {
    let title: string = "Mitarbeiter '" + this.originalSelectedEmployee.username + "' (ID: " + this.originalSelectedEmployee.id + ") zum Abteilungsleiter ernennen?";
    swal.default.fire({
      title: title,
      icon: "warning",
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
        this.makeAdmin();
      } else if (boxResult.isDenied) {
      }
    });
  }

  private makeAdmin(){
    if(this.originalSelectedEmployee && this.originalSelectedEmployee.id){
      this.empService.changeEmployeeRole("SHIFTADMIN", this.originalSelectedEmployee.id);
      this.openSavedMessageBox();
    }
  }

  private deleteEmployee(){
    if(this.originalSelectedEmployee && this.originalSelectedEmployee.id) {
      this.empService.deleteEmployee(this.originalSelectedEmployee.id);
      this.openSavedMessageBox();

      if(this.originalSelectedEmployee.id == this.loginService.LoggedInUser.id){
        this.loginService.Logout();
      }
    }
    else {
      this.openFailedMessageBox("Konnte nicht gespeichert werden!", "Mitarbeiter konnte nicht gefunden werden!");
    }
  }

  private sendEmployeeChangesToDb(changes: {[key: string]: object}): boolean{
    if(this.selectedEmployee) {
      if (this.originalSelectedEmployee.id && this.selectedEmployee.id) {
        this.empService.updateEmployee(changes, this.originalSelectedEmployee.id);

        let newPicture: string | undefined;
        if(changes["base64ProfilePic"]) {
          newPicture = this.selectedEmployee.base64ProfilePic;
        }

//        this.originalSelectedEmployee = JSON.parse(JSON.stringify(this.selectedEmployee));
        if(this.loggedInUserView){
          this.loginService.updateLoggedInUser(JSON.parse(JSON.stringify(this.selectedEmployee)));
          this.originalSelectedEmployee = this.loginService.LoggedInUser;
        }
        else {
          this.loadProfile(this.originalSelectedEmployee.id);
        }

        if(newPicture != undefined) {
          NavbarComponent.profilePictureBase64 = newPicture;
        }

        return true;
      } else {
        return false;
      }
    }

    return false;
  }

  private sendEmployeePasswordChangesToDb(): boolean{
    if(this._LoggedInUserView) {
      let updatedPassword = new UpdatedPassword(this.newPassword, this.newPasswordAgain);
      this.empService.updateEmployeePassword(updatedPassword);
      return true;
    }

    if(this._AdminView && !this._LoggedInUserView){
      let updatedPassword = new UpdatedPassword(this.newPassword, this.newPassword);
      console.log(updatedPassword)
      if (this.originalSelectedEmployee.id != null) {
        this.empService.updateEmployeePasswordById(updatedPassword, this.originalSelectedEmployee.id)
        return true;
      }
    }

    return false;
  }

  private loadProfile(empId: number) {
    this.empService.getEmployeeById(empId).subscribe(emp => {
      this.selectedEmployee = JSON.parse(JSON.stringify(emp)); // Json used to create a copy
      this.originalSelectedEmployee = emp;

      console.info(emp)

      if(!this.selectedEmployee) {
        this.openFailedMessageBox("Fehler beim Aufruf!", "Profilseite von dem Benutzer konnte nicht gefunden werden!");
        this.location.back();
      }

    });
  }

  private loadLoggedInProfile(){
    if (this.loginService.isUserLoggedIn()) {
      this.selectedEmployee = JSON.parse(JSON.stringify(this.loginService.LoggedInUser)); // Json used to create a copy
      this.originalSelectedEmployee = this.loginService.LoggedInUser;
      this.loadShiftTypes();
    }
  }

  private loadShiftTypes() {
    if (this.loginService.LoggedInUser.departmentId != null) {
      this.departmentService.getShifttypesFromDepartment(this.loginService.LoggedInUser.departmentId).subscribe(res => {
        this.allShiftTypes = res;
      })
    }
  }

  private openFailedMessageBox(title: string, text: string){
    swal.default.fire({
      title: title,
      html: text,
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

}
