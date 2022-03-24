import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {Department} from "../../models/dto/Department";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DepartmentListComponent implements OnInit{
  private departments: Department[] = [];
  public selection: SelectionModel<Department>;
  public searchkey = "";

  constructor(private depService: DepartmentService) {
    this.selection = new SelectionModel<Department>(true, []);
  }

  ngOnInit() {
    this.depService.getAllDepartments().subscribe(res => {
        res.departments?.forEach(temp => {
          this.departments.push(temp);
        })
        this.selection = new SelectionModel<Department>(true, res.departments);
      }, error => {
      this.selection = new SelectionModel<Department>(true, []);
      console.log("There was an error getting the Departments: ", error);
    })
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
