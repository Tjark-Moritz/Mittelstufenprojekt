import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";
import {EmployeeService} from "../../services/employee.service";
import {AddDepartment} from "../../models/dto/AddDepartment";

@Component({
  selector: 'app-department-details',
  templateUrl: './department-details.component.html',
  styleUrls: ['./department-details.component.css']
})
export class DepartmentDetailsComponent implements OnInit {

  activeDep: GetDepartment = new GetDepartment();

  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private dialogRef: MatDialogRef<DepartmentDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetDepartment) {

    this.activeDep = data;
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

    let addDepObj: AddDepartment = new AddDepartment();
    addDepObj.leadEmployeeId = leadEmpId;
    addDepObj.abbreviatedName = (document.getElementById("abbNameInputNew") as HTMLInputElement).value;
    addDepObj.name = (document.getElementById("nameInputNew") as HTMLInputElement).value;
    addDepObj.employeesIds = [leadEmpId];

    // Ende / Fehlerausgabe
    if(anyError){
      alert(errorMessage);
    }
    else {
      this.departmentService.addDepartment(addDepObj);
      this.dialogRef.close({ event: 'close', data: undefined });
    }
  }

  delete() {
    // http Anfrage
    this.dialogRef.close({ event: 'close', data: undefined });
  }
}
