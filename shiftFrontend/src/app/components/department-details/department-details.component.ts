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
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";



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















  sortEmpQual(){
    /*
    if(this.localEmpDetails){
      if(this.localEmpDetails.skillSet){
        this.localEmpDetails.skillSet.sort((obj1, obj2) => {
          // @ts-ignore
          if (obj1.designation > obj2.designation) {
            return 1;
          }
          // @ts-ignore
          if (obj1.designation < obj2.designation) {
            return -1;
          }
          return 0;
        });
      }
    }
    */
  }

  async getEmpAndQualById(id: number){
    /*
    this.localEmp = new Employee(id);
    this.empService.getEmployee(this.localEmp).subscribe(data => {
      this.localEmp = data;
    });

    if(!this.localEmp){
      this.localEmp = new Employee();
    }
    if(this.localEmp.id){
      this.empService.getEmployeeQualifications(this.localEmp).subscribe(data => {
        this.localEmpDetails = data;
      });
    }
    this.sortEmpQual();
    */
  }

  getEmpAndQual(){
    return 0;
  }
  /*
  getEmpAndQual(employee: Employee, empDetails: EmployeeDetails){
    this.localEmp = employee;
    this.localEmpDetails = empDetails;
    this.sortEmpQual();
  }
  */

  async deleteEmp(){
    /*
    if(this.localEmp){
      if(confirm("Are you sure to delete " + this.localEmp.lastName + ", " + this.localEmp.firstName + " with the id: " + this.localEmp.id + "?")) {
        await this.empService.deleteEmployee(this.localEmp);
      }

    }
    else {
      console.log("Kein Emp ausgewählt")
    }

    await new Promise(f => setTimeout(f, 100));
    await this.router.navigate(["employees"]);
     */
  }

  async deleteQual(){
  }

  /*
  async deleteQual(qualification: Qualification){
    if(this.localEmp){
      this.delQualArray.push(qualification);
      if(this.localEmpDetails){
        if(this.localEmpDetails.skillSet){
          let retour = this.localEmpDetails.skillSet.findIndex(value => {
            return value == qualification;
          })
          this.localEmpDetails?.skillSet?.splice(retour, 1);
        }
      }
    }
    else {
      console.log("Kein Emp ausgewählt")
    }
    this.sortEmpQual();
  }
  */

  async saveEmp(){
    /*
    let isUpdate = true;
    if(!this.localEmp){
      isUpdate = false;
      // this.localEmp = new Employee();
    }
    this.localEmp.firstName = (document.getElementById("empDetCompFirstName") as HTMLInputElement).value;
    this.localEmp.lastName = (document.getElementById("empDetCompLastName") as HTMLInputElement).value;
    this.localEmp.street = (document.getElementById("empDetCompStreet") as HTMLInputElement).value;
    this.localEmp.postcode = (document.getElementById("empDetCompPostcode") as HTMLInputElement).value;
    this.localEmp.city = (document.getElementById("empDetCompCity") as HTMLInputElement).value;
    this.localEmp.phone = (document.getElementById("empDetCompPhone") as HTMLInputElement).value;

    // Abfrage, ob Daten valide sind
    if(this.localEmp.firstName == "" || this.localEmp.lastName == "" || this.localEmp.street == "" || this.localEmp.city == "" || this.localEmp.phone == "") {
      alert("All inputs need to be filled!");
      return;
    }

    if(this.localEmp.postcode.length != 5){
      alert("Postcode need to have 5 numbers");
      return;
    }
    // Anpassen der hinzugefügten und gelöschten Qualifikationen um dopplungen zu vermeiden
    let delQualArrayLen = this.delQualArray.length
    for (let i = 0; i < delQualArrayLen; i++) {
      let retour = this.addQualArray.findIndex(value => {
        return value == this.delQualArray[i];
      });
      if(retour >= 0){
        this.delQualArray.splice(i, 1);
        this.addQualArray.splice(retour, 1);
        delQualArrayLen = this.delQualArray.length;
        i = i - 1;
      }
    }

    // Employee anpassen
    if(isUpdate){
      // Update
      // await this.empService.updateEmployee(this.localEmp);
    }
    else {
      // Create
      // await this.empService.saveEmployee(this.localEmp);
    }

    // Qualifikationen anpassen
    for (let i = 0; i < this.delQualArray.length; i++) {
      // await this.empService.deleteQualificationFromEmployee(this.localEmp, this.delQualArray[i]);
    }

    for (let i = 0; i < this.addQualArray.length; i++) {
      // await this.empService.addQualificationToEmployee(this.localEmp, this.addQualArray[i]);
    }

    await new Promise(f => setTimeout(f, 100));
    this.router.navigate(["deps"]);
    */
  }

  cancel(){
    //this.router.navigate(["deps"]);
  }

  async openModal(){
    /*
    let modal = this.dialog.open(AddEmployeeModalComponent, {data: this.localEmp});
    let observable: Observable<any>;
    observable = modal.afterClosed();
    observable.subscribe(data => {
      if(data.data == undefined){
        return;
      }
      if(this.localEmpDetails){
        let temp: Qualification[];
        temp = data.data;
        this.addQualArray = temp.filter(value => {

          if(this.localEmpDetails?.skillSet){ // NOT UNDEF

            return this.localEmpDetails.skillSet.find(v2 => {

              if(v2.designation == value.designation){ // wenn beide Qual gleich sind
                return true;
              }
              else {
                let retour = this.delQualArray.findIndex(v3 => {
                  return v3 == value;
                });
                this.delQualArray.splice(retour, 1);
                return retour >= 0;
              }
            }) == undefined;
          }
          else {
            return;
          }
        });
        this.localEmpDetails.skillSet = data.data;
        this.sortEmpQual();
      }
    });
    */
  }
}
