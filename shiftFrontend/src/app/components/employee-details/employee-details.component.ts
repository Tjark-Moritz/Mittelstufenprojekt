import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";
import {EmployeeService} from "../../services/employee.service";
import {GetEmployee} from "../../models/dto/GetEmployee";
import {AddDepartment} from "../../models/dto/AddDepartment";
import {AddEmployee} from "../../models/dto/AddEmployee";

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {

  activeEmp: GetEmployee = new GetEmployee();
  isOld: boolean = true;
  // Kein Admin benötigt, da Admin bei existierendem Benutzer umgeleitet wird

  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private dialogRef: MatDialogRef<EmployeeDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetEmployee) {

    this.activeEmp = data;

    if(this.activeEmp.id == undefined){
      this.isOld = false;
    }

    if(this.activeEmp.departmentId){
      departmentService.getDepartmentById(this.activeEmp.departmentId).subscribe(res => {
        let tempDepName: string = "";
        tempDepName += res.name;
        (document.getElementById("depNameInputExists") as HTMLInputElement).value = tempDepName;
      });
    }
  }
  ngOnInit() {
  }

  close() {
    this.dialogRef.close({ event: 'close', data: undefined });
  }

  save() {

    let anyError: boolean = false;
    let errorMessage: string = "";

    let username: string = (document.getElementById("userNameNew") as HTMLInputElement).value;
    let lastName: string = (document.getElementById("lastNameNew") as HTMLInputElement).value;
    let firstName: string = (document.getElementById("firstNameNew") as HTMLInputElement).value;
    let street: string = (document.getElementById("streetNew") as HTMLInputElement).value;
    let zipcode: string = (document.getElementById("zipcodeNew") as HTMLInputElement).value;
    let city: string = (document.getElementById("cityNew") as HTMLInputElement).value;
    let phone: string = (document.getElementById("phoneNew") as HTMLInputElement).value;
    let email: string = (document.getElementById("eMailNew") as HTMLInputElement).value;
    let numHolidaysLeft: number = +(document.getElementById("numHolidaysLeftNew") as HTMLInputElement).value;
    let base64ProfilePic: string = (document.getElementById("base64ProfilePicNew") as HTMLInputElement).value;

    if(username == ""){
      errorMessage += "Benutzername falsch, "
    }
    if(lastName == ""){
      errorMessage += "Nachname falsch, "
    }
    if(firstName == ""){
      errorMessage += "Vorname falsch, "
    }
    if(street == ""){
      errorMessage += "Straße falsch, "
    }
    if(zipcode == ""){
      errorMessage += "PLZ falsch, "
    }
    if(city == ""){
      errorMessage += "Stadt falsch, "
    }
    if(phone == ""){
      errorMessage += "Telefon falsch, "
    }
    if(email == ""){
      errorMessage += "E-Mail falsch, "
    }
    if(numHolidaysLeft == 0){
      errorMessage += "Urlaubstage übrig falsch, "
    }
    if(base64ProfilePic == ""){
      errorMessage += "Bild falsch, "
    }


    if(anyError){
      alert(errorMessage);
    }
    else {
      let newEmp: AddEmployee = new AddEmployee(username, lastName, firstName, street, zipcode, city, phone, email, numHolidaysLeft, base64ProfilePic);
      this.employeeService.addEmployee(newEmp);
      this.dialogRef.close({ event: 'close', data: undefined });
    }
  }

  delete() {
    // Wird vermutlich nicht benötigt
  }
}
