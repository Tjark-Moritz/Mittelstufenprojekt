import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";
import {EmployeeService} from "../../services/employee.service";
import {AddDepartment} from "../../models/dto/AddDepartment";
import {GetEmployee} from "../../models/dto/GetEmployee";

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {

  activeEmp: GetEmployee = new GetEmployee();
  isOld: boolean = false;
  isAdmin: boolean = true; // Muss geändert werden

  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private dialogRef: MatDialogRef<EmployeeDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetDepartment) {

    this.activeEmp = data;
    if(this.activeEmp.id){
      // Abteilung vorhanden
      if(this.isAdmin){
        if(this.activeEmp.departmentId){
          departmentService.getDepartmentById(this.activeEmp.departmentId).subscribe(res => {
            let tempDepName: string = "";
            tempDepName += res.name;
            (document.getElementById("depNameInputNew") as HTMLInputElement).value = tempDepName;
          });
        }
      }
      else {
        if(this.activeEmp.departmentId){
          departmentService.getDepartmentById(this.activeEmp.departmentId).subscribe(res => {
            let tempDepName: string = "";
            tempDepName += res.name;
            (document.getElementById("depNameInputExists") as HTMLInputElement).value = tempDepName;
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
    /*
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

      if(this.activeEmp.leadEmployee != leadEmpId){
        changes = true;
        depChanges["leadEmployee"] = leadEmpId.toString();
      }
      let depName: string = (document.getElementById("nameInputNew") as HTMLInputElement).value;
      if(this.activeEmp.name != depName){
        changes = true;
        depChanges["name"] = depName;
      }
      let depAbbrName: string = (document.getElementById("abbNameInputNew") as HTMLInputElement).value;
      if(this.activeEmp.abbreviatedName != depAbbrName){
        changes = true;
        depChanges["abbreviatedName"] = depAbbrName;
      }
      if(!anyError && changes){
        if(this.activeEmp.departmentId){
          this.departmentService.updateDepartment(depChanges, this.activeEmp.departmentId);
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
    if(this.activeEmp.id){
      this.employeeService.deleteEmployee(this.activeEmp.id);
    }
    this.dialogRef.close({ event: 'close', data: undefined });
  }
}
