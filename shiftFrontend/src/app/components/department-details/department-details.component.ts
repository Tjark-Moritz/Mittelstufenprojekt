import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";

@Component({
  selector: 'app-department-details',
  templateUrl: './department-details.component.html',
  styleUrls: ['./department-details.component.css']
})
export class DepartmentDetailsComponent implements OnInit {

  activeDep: GetDepartment = new GetDepartment();

  constructor(private departmentService: DepartmentService,
              private dialogRef: MatDialogRef<DepartmentDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetDepartment) {

    this.activeDep = data;
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
