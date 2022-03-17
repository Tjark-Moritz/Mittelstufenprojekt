import { Component} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {Department} from "../../models/dto/Department";

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css']
})
export class DepartmentListComponent {

  public departments: Department[] = [];

  constructor(private depService: DepartmentService) {
    this.getDepartments();
  }

  getDepartments(){
    this.depService.getAllDepartments().subscribe((val) => {
      if(val.departments){
        this.departments = val.departments;
      }

    })
  }
}
