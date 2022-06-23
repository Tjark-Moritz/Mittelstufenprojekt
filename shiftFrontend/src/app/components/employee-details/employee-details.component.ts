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

    let newEmp: AddEmployee = new AddEmployee(username, lastName, firstName, street, zipcode, city, phone, email, numHolidaysLeft, base64ProfilePic);
    console.log(newEmp);
    this.employeeService.addEmployee(newEmp);
    return;








    /*
    let tempFN: string = (document.getElementById("leadEmpFirstNameInputNew") as HTMLInputElement).value;
    let tempLN: string = (document.getElementById("leadEmpLastNameInputNew") as HTMLInputElement).value;
    let leadEmpId = await this.employeeService.getEmployeeIdByFirstAndLastName(tempFN, tempLN);
    if(leadEmpId == -1){
      anyError = true
      errorMessage += "Mitarbeiter nicht gefunden. ";
    }

    if(!this.isOld){
      let addDepObj: AddDepartment = new AddDepartment();
      addDepObj.leadEmployeeId = leadEmpId;
      addDepObj.abbreviatedName = (document.getElementById("abbNameInputNew") as HTMLInputElement).value;
      addDepObj.name = (document.getElementById("nameInputNew") as HTMLInputElement).value;
      addDepObj.employeeIds = [leadEmpId];

      if(addDepObj.name == ""){
        anyError = true
        errorMessage += "Abteilungsname nicht eingetragen. ";
      }
      if(addDepObj.abbreviatedName == ""){
        anyError = true
        errorMessage += "Abteilungskürzel nicht eingetragen. ";
      }
      if(!anyError){
        this.departmentService.addDepartment(addDepObj);
      }
    }
    else {
      let depChanges: {[key: string]: string} = {}
      let changes: boolean = false;

      if(this.activeDep.leadEmployee != leadEmpId){
        changes = true;
        depChanges["leadEmployee"] = leadEmpId.toString();
      }
      let depName: string = (document.getElementById("nameInputNew") as HTMLInputElement).value;
      if(this.activeDep.name != depName){
        changes = true;
        depChanges["name"] = depName;
      }
      let depAbbrName: string = (document.getElementById("abbNameInputNew") as HTMLInputElement).value;
      if(this.activeDep.abbreviatedName != depAbbrName){
        changes = true;
        depChanges["abbreviatedName"] = depAbbrName;
      }
      if(!anyError && changes){
        if(this.activeDep.departmentId){
          this.departmentService.updateDepartment(depChanges, this.activeDep.departmentId);
        }
      }
    }

    if(anyError){
      alert(errorMessage);
    }
    else {
      this.dialogRef.close({ event: 'close', data: undefined });
      // deps neuladen
    }

     */
  }

  delete() {
    // Wird vermutlich nicht benötigt
  }
}
