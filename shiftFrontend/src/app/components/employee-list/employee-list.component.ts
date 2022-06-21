import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {SelectionModel} from "@angular/cdk/collections";
import {GetEmployee} from "../../models/dto/GetEmployee";
import {EmployeeService} from "../../services/employee.service";
import {MatDialog} from "@angular/material/dialog";
import {EmployeeDetailsComponent} from "../employee-details/employee-details.component";
import {Observable} from "rxjs";
import {BearerTokenService} from "../../services/bearer-token.service";
import {UserRoleEnum} from "../../models/UserRoleEnum";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EmployeeListComponent implements OnInit {

  private employees: GetEmployee[] = [];
  public selection: SelectionModel<GetEmployee>;
  private selectionLength = 0;
  private depIdAbbrDict: { [id: number]: GetDepartment; }  = {};
  public searchkey = "";
  public emptyEmp = new GetEmployee();

  isAdmin: boolean = false; // Muss geändert werden

  constructor(private empService: EmployeeService, private depService: DepartmentService, private dialog: MatDialog) {
    this.selection = new SelectionModel<GetEmployee>(true, []);

    let roleName: UserRoleEnum | undefined;
    // @ts-ignore
    roleName = BearerTokenService.getUserRole;
    if(roleName){
      if(roleName == UserRoleEnum.Admin){
        this.isAdmin = true;
      }
    }

    this.empService.getAllEmployees().subscribe(res => {
      res.forEach(temp => {
        this.employees.push(temp);
      })
      this.selection = new SelectionModel<GetEmployee>(true, res);
    }, error => {
      this.selection = new SelectionModel<GetEmployee>(true, []);
      console.log("There was an error getting the Departments: ", error);
    })
    this.selectionLength = this.selection.selected.length;

    this.depService.getAllDepartments().subscribe(res => {
      res.forEach(temp => {
        if(temp.departmentId){
          this.depIdAbbrDict[temp.departmentId] = temp;
        }
      })
    })
  }

  ngOnInit() {
  }

  getDepAbbr(depId: number| undefined){
    if(depId && depId != 0){
      return this.depIdAbbrDict[depId].abbreviatedName;
    }
    else {
      return ("None");
    }
  }

  getDepName(depId: number| undefined){
    if(depId && depId != 0){
      return this.depIdAbbrDict[depId].name;
    }
    else {
      return ("None");
    }
  }

  openModal(employee: GetEmployee){

    if(this.isAdmin){
      // Lennarts part aufrufen
      return;
    }

    let modal = this.dialog.open(EmployeeDetailsComponent, {
      position: {
        top: "0",
        right: "0",
      },
      height: "100vh",
      width: "100vh",
      direction: "ltr",
      data: employee
    });

    let observable: Observable<any>;
    observable = modal.afterClosed();
    observable.subscribe(data => {

    });
  }

  async search(){
    // Puffer damit der eingegebene Text lädt
    await new Promise(f => setTimeout(f, 100));
    this.selection.clear();
    this.employees.forEach(row => this.selection.select(row));

    this.searchkey = (document.getElementById("inputSearch") as HTMLInputElement).value;
    if(this.searchkey != ""){
      let newFound = true;
      while(newFound){
        newFound = false;
        let notFitting = this.selection.selected.find(value => {
          if(value.lastName && value.firstName){
            if(value.lastName.includes(this.searchkey)){
              return;
            }
            if(value.firstName.includes(this.searchkey)){
              return;
            }
            let depAbbr = this.getDepAbbr(value.departmentId);
            if(depAbbr && depAbbr.includes(this.searchkey)){
              return;
            }
            let depName = this.getDepName(value.departmentId);
            if(depName && depName.includes(this.searchkey)){
              return;
            }
          }
          return value;
        })
        if(notFitting){
          this.selection.deselect(notFitting);
          newFound = true;
        }
      }
    }
  }
}
