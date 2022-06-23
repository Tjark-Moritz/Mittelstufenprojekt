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
import {GetShiftType} from "../../models/dto/GetShiftType";
import {ShiftTypeService} from "../../services/shift-type.service";
import {DepartmentService} from "../../services/department.service";

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
  public set selectedShiftType(val) {
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

  ngOnInit(): void {
    this.para = this.route.params.subscribe(params => {
      if(Object.keys(params).length == 0) {
        if (this.loginService.LoggedInUser) {
          this.selectedEmployee = JSON.parse(JSON.stringify(this.loginService.LoggedInUser)); // Json used to create a copy
          this.originalSelectedEmployee = this.loginService.LoggedInUser;
          this.loadShiftTypes();
        }
      }
      else {
        this.loadProfile(params['id']);
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

    //Todo remove cutom setter
    this._LoggedInUserView = true;
    this._AdminView = true;
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
      let empChanges: {[key: string]: string} = {};
      let detailsAreChanged: boolean = false;

      if (this.originalSelectedEmployee.lastName != this.selectedEmployee.lastName) {
        empChanges["lastName"] = this.selectedEmployee.lastName;
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.firstName != this.selectedEmployee.firstName) {
        empChanges["firstName"] = this.selectedEmployee.firstName;
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.street != this.selectedEmployee.street){
        empChanges["street"] = this.selectedEmployee.street;
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.zipcode != this.selectedEmployee.zipcode){
        empChanges["zipcode"] = this.selectedEmployee.zipcode;
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.city != this.selectedEmployee.city){
        empChanges["city"] = this.selectedEmployee.city;
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.phone != this.selectedEmployee.phone){
        empChanges["phone"] = this.selectedEmployee.phone;
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.email != this.selectedEmployee.email){
        empChanges["email"] = this.selectedEmployee.email;
        detailsAreChanged = true;
      }

      if (this.originalSelectedEmployee.base64ProfilePic != this.selectedEmployee.base64ProfilePic){
        empChanges["base64ProfilePic"] = this.selectedEmployee.base64ProfilePic;
        detailsAreChanged = true;
      }

      let detailsChangesResult: boolean = true;
      if(detailsAreChanged) {
        this.sendEmployeeChangesToDb(empChanges);
        if(!detailsChangesResult){
          this.openFailedMessageBox("Mitarbeiternummer ist nicht definiert!<br/>Details wurden nicht gespeichert!");
        }
      }

      let passwordChangesResult: boolean = true;
      if((this.newPassword != "" && this.adminView && !this.loggedInUserView) || (this.newPassword != "" && this.newPasswordAgain != "" && this.loggedInUserView)){
        passwordChangesResult = this.sendEmployeePasswordChangesToDb();

        if(!passwordChangesResult)
        {
          this.openFailedMessageBox("Passwort konnte nicht gespeichert werden!");
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
          here.openFailedMessageBox("Das ausgewählte Bild überschreitet leider die maximale Dateigröße!<br/>" +
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

  private sendEmployeeChangesToDb(changes: {[key: string]: string}): boolean{
    if(this.selectedEmployee) {
      if (this.originalSelectedEmployee.id && this.selectedEmployee.id) {
        this.empService.updateEmployee(changes, this.originalSelectedEmployee.id);
        this.originalSelectedEmployee = JSON.parse(JSON.stringify(this.selectedEmployee));
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
      this.loginService.updateLoggedInUser();
      return true;
    }

    if(this._AdminView && !this._LoggedInUserView){
      let updatedPassword = new UpdatedPassword(this.newPassword, this.newPasswordAgain);
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
    });
  }

  private loadShiftTypes() {
    if (this.loginService.LoggedInUser.departmentId != null) {
      this.departmentService.getShifttypesFromDepartment(this.loginService.LoggedInUser.departmentId).subscribe(res => {
        //this.allShiftTypes = res;  // Todo: uncomment
      })
    }

    // Todo: Remove dummy data
    let shift1: GetShiftType = new GetShiftType(1, new Date(0, 0, 0, 5, 0, 0, 0), new Date(0, 0, 0, 10, 0, 0, 0),1, "Frühschicht", "#f0aa13");
    let shift2: GetShiftType = new GetShiftType(2, new Date(0, 0, 0, 10, 0, 0, 0), new Date(0, 0, 0, 15, 0, 0, 0), 1,"Mittelschicht", "#47f013");
    let shift3: GetShiftType = new GetShiftType(3, new Date(0, 0, 0, 15, 0, 0, 0), new Date(0, 0, 0, 20, 0, 0, 0), 1,"Spätschicht", "#0ce4f7");
    this.allShiftTypes.push(shift1);
    this.allShiftTypes.push(shift2);
    this.allShiftTypes.push(shift3);
    let combinedShifts: GetShiftType[] = [];
    combinedShifts.push(shift1);
    combinedShifts.push(shift2);
    combinedShifts.push(shift3);
    this.allShiftTypes = combinedShifts;
  }

  private openFailedMessageBox(text: string){
    swal.default.fire({
      title: "Konnte nicht gespeichert werden!",
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
