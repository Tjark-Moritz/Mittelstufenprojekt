import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {SelectionModel} from "@angular/cdk/collections";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";
import {EmployeeService} from "../../services/employee.service";
import {GetEmployee} from "../../models/dto/GetEmployee";

@Component({
  selector: 'app-department-details',
  templateUrl: './department-details.component.html',
  styleUrls: ['./department-details.component.css']
})
export class DepartmentDetailsComponent implements OnInit {

  displayedColumns: string[] = ['select', 'lastName', 'firstName'];
  // @ts-ignore
  selection: SelectionModel<GetDepartment>;
  // @ts-ignore
  allEmployees: GetDepartment[];
  // @ts-ignore
  dataSource: MatTableDataSource<GetDepartment>;
  // @ts-ignore
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private dialogRef: MatDialogRef<DepartmentDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetEmployee) {
  }
  ngOnInit() {
    /*
    this.departmentService.getQualificationEmployees(this.data).subscribe(res => {
      this.selection = new SelectionModel<GetEmployee>(true, res.employees);
    }, error => {
      this.selection = new SelectionModel<GetEmployee>(true, [])
      console.log("There was an error getting the Employees for the given Qualification: ", error);
    })

    this.employeeService.getAllEmployees().subscribe(
      (res: Employee[]) => {
        this.allEmployees = res;
        this.dataSource = new MatTableDataSource<Employee>(this.allEmployees);
        this.dataSource.sort = this.sort;
      });
     */
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  close() {
    this.dialogRef.close({ event: 'close', data: undefined });
  }

  save() {
    this.dialogRef.close({ event: 'close', data: this.selection.selected });
  }

  OnChanged(event: MatCheckboxChange, row: GetEmployee) {
    let temp = this.selection.selected.find(item => item.departmentId === row.id)
    temp !== undefined ?
      this.selection.deselect(temp) : this.selection.select(row);
  }

  GetCheckedState(row: GetEmployee): boolean{
    return this.selection.selected.some(item => item.departmentId === row.id);
  }

}
