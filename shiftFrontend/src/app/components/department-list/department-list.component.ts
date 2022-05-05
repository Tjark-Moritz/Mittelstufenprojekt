import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {DepartmentService} from "../../services/department.service";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {SelectionModel} from "@angular/cdk/collections";
import {Observable} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {DepartmentDetailsComponent} from "../department-details/department-details.component";

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

  constructor(private depService: DepartmentService, private dialog: MatDialog) {
    this.selection = new SelectionModel<GetDepartment>(true, []);
  }

  ngOnInit() {
    this.depService.getAllDepartments().subscribe(res => {
        res.departmentDto?.forEach(temp => {
          this.departments.push(temp);
        })
        this.selection = new SelectionModel<GetDepartment>(true, res.departmentDto);
      }, error => {
      this.selection = new SelectionModel<GetDepartment>(true, []);
      console.log("There was an error getting the Departments: ", error);
    })
  }

  test(){

    this.departments[0] = new GetDepartment();
    let modal = this.dialog.open(DepartmentDetailsComponent, {
      position: {
        top: "0",
        right: "0",
      },
      height: "100vh",
      direction: "rtl",
      data: this.departments[0]
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
