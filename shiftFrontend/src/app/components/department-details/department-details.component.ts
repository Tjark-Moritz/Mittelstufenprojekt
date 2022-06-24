import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GetDepartment} from "../../models/dto/GetDepartment";
import {DepartmentService} from "../../services/department.service";
import {EmployeeService} from "../../services/employee.service";
import {AddDepartment} from "../../models/dto/AddDepartment";
import {UserRoleEnum} from "../../models/UserRoleEnum";
import {BearerTokenService} from "../../services/bearer-token.service";
import {AddShiftType} from "../../models/dto/AddShiftType";

@Component({
  selector: 'app-department-details',
  templateUrl: './department-details.component.html',
  styleUrls: ['./department-details.component.css']
})
export class DepartmentDetailsComponent implements OnInit {

  activeDep: GetDepartment = new GetDepartment();
  isOld: boolean;
  isAdmin: boolean;
  shiftCount: number;

  constructor(private departmentService: DepartmentService,
              private employeeService: EmployeeService,
              private dialogRef: MatDialogRef<DepartmentDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) private data: GetDepartment, private bearerTokenService: BearerTokenService) {
    this.isAdmin = false;
    this.isOld = false;
    this.shiftCount = 0;

    let roleName: UserRoleEnum | undefined;
    // @ts-ignore
    roleName = this.bearerTokenService.getUserRole;
    if(roleName){
      if(roleName == UserRoleEnum.Admin){
        this.isAdmin = true;
      }
    }
    this.activeDep = data;
    if(this.activeDep.departmentId){
      // Abteilung vorhanden
      if(this.isAdmin){
        if(this.activeDep.leadEmployee){
          employeeService.getEmployeeById(this.activeDep.leadEmployee).subscribe(res => {
            let tempFN: string = "";
            tempFN += res.firstName;
            (document.getElementById("leadEmpFirstNameInputNew") as HTMLInputElement).value = tempFN;
            let tempLN: string = "";
            tempLN += res.lastName;
            (document.getElementById("leadEmpLastNameInputNew") as HTMLInputElement).value = tempLN;
          });
        }
      }
      this.isOld = true;
    }
  }
  ngOnInit() {
  }

  close() {
    this.dialogRef.close({ event: 'close', data: undefined });
  }

  async save() {

    let anyError: boolean = false;
    let errorMessage: string = "";
    // gibt es den Abteilungsleiter
    let tempFN: string = (document.getElementById("leadEmpFirstNameInputNew") as HTMLInputElement).value;
    let tempLN: string = (document.getElementById("leadEmpLastNameInputNew") as HTMLInputElement).value;
    let leadEmpId = await this.employeeService.getEmployeeIdByFirstAndLastName(tempFN, tempLN);
    if(leadEmpId == -1){
      anyError = true
      errorMessage += "Mitarbeiter nicht gefunden. ";
    }

    if(!this.isOld){
      let addDepObj: AddDepartment = new AddDepartment();
      addDepObj.leadEmployeeId = leadEmpId;
      addDepObj.abbreviatedName = (document.getElementById("abbNameInputNew") as HTMLInputElement).value;
      addDepObj.name = (document.getElementById("nameInputNew") as HTMLInputElement).value;
      addDepObj.employeeIds = [leadEmpId];

      if(addDepObj.name == ""){
        anyError = true
        errorMessage += "Abteilungsname nicht eingetragen. ";
      }
      if(addDepObj.abbreviatedName == ""){
        anyError = true
        errorMessage += "Abteilungskürzel nicht eingetragen. ";
      }

      addDepObj.shiftTypes = [];

      for (let i = 0; i < this.shiftCount; i++) {
        console.log(this.shiftCount);
        let shiftStart: string = (document.getElementById("shiftStartNew" + i) as HTMLInputElement).value;
        let shiftEnd: string = (document.getElementById("shiftEndNew" + i) as HTMLInputElement).value;
        let empCount: number = +(document.getElementById("shiftEmpNumNew" + i) as HTMLInputElement).value;
        let shiftName: string = (document.getElementById("shiftTypeNameNew" + i) as HTMLInputElement).value;
        let shiftColor: string = (document.getElementById("shiftColorNew" + i) as HTMLInputElement).value;

        let dateStart: Date = new Date();
        let splittedStart = shiftStart.split(":");
        let hs: number = +splittedStart[0];
        let ms: number = +splittedStart[1];
        let ss: number = +splittedStart[2];
        dateStart.setHours(hs, ms, ss);

        let dateEnd: Date = new Date();
        let splittedEnd = shiftEnd.split(":");
        let he: number = +splittedEnd[0];
        let me: number = +splittedEnd[1];
        let se: number = +splittedEnd[2];
        dateEnd.setHours(he, me, se);

        let shift: AddShiftType = new AddShiftType(dateStart, dateEnd, empCount, shiftName, shiftColor);
        if(addDepObj.shiftTypes){
          addDepObj.shiftTypes.push(shift);
        }
      }

      console.log(addDepObj);

      if(!anyError){
        this.departmentService.addDepartment(addDepObj);
      }
    }
    else {
      let depChanges: {[key: string]: string} = {}
      let changes: boolean = false;

      if(this.activeDep.leadEmployee != leadEmpId){
        changes = true;
        depChanges["leadEmployee"] = leadEmpId.toString();
      }
      let depName: string = (document.getElementById("nameInputNew") as HTMLInputElement).value;
      if(this.activeDep.name != depName){
        changes = true;
        depChanges["name"] = depName;
      }
      let depAbbrName: string = (document.getElementById("abbNameInputNew") as HTMLInputElement).value;
      if(this.activeDep.abbreviatedName != depAbbrName){
        changes = true;
        depChanges["abbreviatedName"] = depAbbrName;
      }
      if(!anyError && changes){
        if(this.activeDep.departmentId){
          this.departmentService.updateDepartment(depChanges, this.activeDep.departmentId);
        }
      }
    }

    if(anyError){
      alert(errorMessage);
    }
    else {
      this.dialogRef.close({ event: 'close', data: undefined });
      // deps neuladen
    }
  }

  delete() {
    if(this.activeDep.departmentId){
      this.departmentService.deleteDepartment(this.activeDep.departmentId);
    }
    this.dialogRef.close({ event: 'close', data: undefined });
  }

  addShifts(){
    let sOld = (document.getElementById("adminDepTable") as HTMLInputElement).innerHTML;
    sOld += this.addShiftString();
    (document.getElementById("adminDepTable") as HTMLInputElement).innerHTML = sOld;
  }

  addShiftString(){


    let s1: string = '<tr><td class="tdLabelName">Start Schicht ' + this.shiftCount + ':</td><td><input class="inputSetName" type="text" id="';
    let s1_1: string = 'shiftStartNew' + this.shiftCount;
    let s2: string = '"></td></tr><tr><td class="tdLabelName">Ende Schicht ' + this.shiftCount + ':</td><td><input class="inputSetName" type="text" id="';
    let s2_1: string = 'shiftEndNew' + this.shiftCount;
    let s3: string = '"></td></tr><tr><td class="tdLabelName">Mitarbeiterzahl ' + this.shiftCount + ':</td><td><input class="inputSetName" type="text" id="';
    let s3_1: string = 'shiftEmpNumNew' + this.shiftCount;
    let s4: string = '"></td></tr><tr><td class="tdLabelName">Schichtname ' + this.shiftCount + ':</td><td><input class="inputSetName" type="text" id="';
    let s4_1: string = 'shiftTypeNameNew' + this.shiftCount;
    let s5: string = '"></td></tr><tr><td class="tdLabelName">Schichtfarbe ' + this.shiftCount + ':</td><td><input class="inputSetName" type="text" id="';
    let s5_1: string = 'shiftColorNew' + this.shiftCount;
    let s6: string = '"></td></tr>';
    let sAll = s1 + s1_1 + s2 + s2_1 + s3 + s3_1 + s4 + s4_1 + s5 + s5_1 + s6 ;
    this.shiftCount++;
    return sAll;
  }

}
