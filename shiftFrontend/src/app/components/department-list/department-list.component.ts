import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DepartmentListComponent implements OnInit{
  private departments: GetDepartment[] = [];
  public selection: SelectionModel<GetDepartment>;
  public searchkey = "";

  constructor(private depService: DepartmentService) {
    this.selection = new SelectionModel<GetDepartment>(true, []);
  }

  ngOnInit() {
    console.log("consturctor");
    this.depService.getAllDepartments().subscribe(res => {
        res.departmentDto?.forEach(temp => {
          this.departments.push(temp);
          console.log(this.departments[0]);
          console.log(this.departments[1]);
        })
        this.selection = new SelectionModel<GetDepartment>(true, res.departmentDto);
      }, error => {
      this.selection = new SelectionModel<GetDepartment>(true, []);
      console.log("There was an error getting the Departments: ", error);
    })
  }

  test(){
    console.log("test");
    this.depService.getAllDepartments();
    this.depService.getDepartmentById(1);
    this.depService.addDepartment(this.departments[0]);
    let depMap = new Map<string, string>();
    depMap.set("name", "Bla");
    depMap.set("leadEmployeeId", "1");
    this.depService.updateDepartment(depMap, 1);
    this.depService.deleteDepartment(1);
  }

  async search(){
    // Puffer damit der eingegebene Text lädt
    await new Promise(f => setTimeout(f, 100));
    this.selection.clear();
    this.departments.forEach(row => this.selection.select(row));

    this.searchkey = (document.getElementById("inputSearch") as HTMLInputElement).value;
    if(this.searchkey != ""){
      let newFound = true;
      while(newFound){
        newFound = false;
        let notFitting = this.selection.selected.find(value => {
          if(value.name && value.abbreviatedName){
            if(value.name.includes(this.searchkey)){
              return;
            }
            if(value.abbreviatedName.includes(this.searchkey)){
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
