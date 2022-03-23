import {Component, OnInit} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {Department} from "../../models/dto/Department";
import {SelectionModel} from "@angular/cdk/collections";
import {Employee} from "../../models/dto/Employee";
import {EmployeeService} from "../../services/employee.service";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  private employees: Employee[] = [];
  public selection: SelectionModel<Employee>;
  private selectionLength = 0;
  private depIdAbbrDict: { [id: number]: Department; }  = {};

  constructor(private empService: EmployeeService, private depService: DepartmentService) {
    this.selection = new SelectionModel<Employee>(true, []);
  }

  ngOnInit() {
    this.empService.getAllEmployees().subscribe(res => {
      res.employees?.forEach(temp => {
        this.employees.push(temp);
      })
      this.selection = new SelectionModel<Employee>(true, res.employees);
    }, error => {
      this.selection = new SelectionModel<Employee>(true, []);
      console.log("There was an error getting the Departments: ", error);
    })
    this.selectionLength = this.selection.selected.length;

    this.depService.getAllDepartments().subscribe(res => {
      res.departments?.forEach(temp => {
        if(temp.departmentId){
          this.depIdAbbrDict[temp.departmentId] = temp;
        }
      })
    })
  }

  getDepAbbr(depId: number| undefined){
    if(depId){
      return this.depIdAbbrDict[depId].abbreviatedName;
    }
    else {
      return ("None");
    }
  }

  getDepName(depId: number| undefined){
    if(depId){
      return this.depIdAbbrDict[depId].name;
    }
    else {
      return ("None");
    }
  }


  async search(){
    // Puffer damit der eingegebene Text lädt
    await new Promise(f => setTimeout(f, 100));
    this.selection.clear();
    this.employees.forEach(row => this.selection.select(row));

    let searchkey = (document.getElementById("inputSearch") as HTMLInputElement).value;
    if(searchkey != ""){
      let newFound = true;
      while(newFound){
        newFound = false;
        let notFitting = this.selection.selected.find(value => {
          if(value.lastname && value.firstname){
            if(value.lastname.includes(searchkey)){
              return;
            }
            if(value.firstname.includes(searchkey)){
              return;
            }
            let depAbbr = this.getDepAbbr(value.departmentId);
            if(depAbbr && depAbbr.includes(searchkey)){
              return;
            }
            let depName = this.getDepName(value.departmentId);
            if(depName && depName.includes(searchkey)){
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
