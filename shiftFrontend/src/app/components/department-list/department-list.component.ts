import {Component, OnInit} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {Department} from "../../models/dto/Department";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css']
})
export class DepartmentListComponent implements OnInit{

  public departments: Department[] = [];
  public selection: SelectionModel<Department>;
  private selectionLength = 0;

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
    this.selectionLength = this.selection.selected.length;
  }


  async search(){
    // Puffer damit der eingegebene Text lädt
    await new Promise(f => setTimeout(f, 100));
    this.selection.clear();
    this.departments.forEach(row => this.selection.select(row));

    console.log(this.selection);

    /*
    // Alle auswählen
    for (let i = 0; i < this.selectionLength; i++) {
      this.selection.select(i);
    }
    */

    let searchkey = (document.getElementById("inputSearch") as HTMLInputElement).value;
    console.log("search");
    console.log(searchkey);
    if(searchkey != ""){
      let newFound = true;
      while(newFound){
        newFound = false;
        let notFitting = this.selection.selected.find(value => {
          if(value.name){
            if(value.name.includes(searchkey)){
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
