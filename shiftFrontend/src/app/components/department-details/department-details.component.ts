import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";
import {GetEmployee} from "../../models/dto/GetEmployee";
import {EmployeeService} from "../../services/employee.service";

@Component({
  selector: 'app-department-details',
  templateUrl: './department-details.component.html',
  styleUrls: ['./department-details.component.css']
})
export class DepartmentDetailsComponent implements OnInit {

  activeDep: GetDepartment = new GetDepartment();
  leadingEmployee: GetEmployee = new GetEmployee();

  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private dialogRef: MatDialogRef<DepartmentDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetDepartment) {

    this.activeDep = data;
    employeeService.getEmployeeIdByFirstAndLastName("string", "string");
  }
  ngOnInit() {
  }

  close() {
    this.dialogRef.close({ event: 'close', data: undefined });
  }

  save() {
    // http Anfragen
    this.dialogRef.close({ event: 'close', data: undefined });
  }

  delete() {
    // http Anfrage
    this.dialogRef.close({ event: 'close', data: undefined });
  }
}
