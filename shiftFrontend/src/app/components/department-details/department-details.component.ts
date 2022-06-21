import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";
import {EmployeeService} from "../../services/employee.service";
import {AddDepartment} from "../../models/dto/AddDepartment";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {BearerTokenService} from "../../services/bearer-token.service";

@Component({
  selector: 'app-department-details',
  templateUrl: './department-details.component.html',
  styleUrls: ['./department-details.component.css']
})
export class DepartmentDetailsComponent implements OnInit {

  activeDep: GetDepartment = new GetDepartment();
  isOld: boolean;
  isAdmin: boolean;

  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private dialogRef: MatDialogRef<DepartmentDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetDepartment) {
    this.isAdmin = false;
    this.isOld = false;

    let roleName: UserRoleEnum | undefined;
    // @ts-ignore
    roleName = BearerTokenService.getUserRole;
    if(roleName){
      if(roleName == UserRoleEnum.Admin){
        this.isAdmin = true;
      }
    }
    this.activeDep = data;
    if(this.activeDep.departmentId){
      // Abteilung vorhanden
      if(this.isAdmin){
        if(this.activeDep.leadEmployee){
          employeeService.getEmployeeById(this.activeDep.leadEmployee).subscribe(res => {
            let tempFN: string = "";
            tempFN += res.firstName;
            (document.getElementById("leadEmpFirstNameInputNew") as HTMLInputElement).value = tempFN;
            let tempLN: string = "";
            tempLN += res.lastName;
            (document.getElementById("leadEmpLastNameInputNew") as HTMLInputElement).value = tempLN;
          });
        }
      }
      this.isOld = true;
    }
  }
  ngOnInit() {
  }

  close() {
    this.dialogRef.close({ event: 'close', data: undefined });
  }

  async save() {

    let anyError: boolean = false;
    let errorMessage: string = "";
    // gibt es den Abteilungsleiter
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
  }

  delete() {
    if(this.activeDep.departmentId){
      this.departmentService.deleteDepartment(this.activeDep.departmentId);
    }
    this.dialogRef.close({ event: 'close', data: undefined });
  }
}
