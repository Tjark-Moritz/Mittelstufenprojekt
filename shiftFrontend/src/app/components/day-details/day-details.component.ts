import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {GetShift} from "../../models/dto/GetShift";
import {SelectionModel} from "@angular/cdk/collections";
import {GetEmployee} from "../../models/dto/GetEmployee";

@Component({
  selector: 'app-day-details',
  templateUrl: './day-details.component.html',
  styleUrls: ['./day-details.component.css']
})
export class DayDetailsComponent implements OnInit {
  date: Date = new Date();
  formattedDate: string = "";

  shifts: GetShift[] = [];

  public selection: SelectionModel<GetEmployee>;
  public searchKey = "";

  constructor(@Inject(MAT_DIALOG_DATA) private data: any) {
    this.date = data.date;
    this.formattedDate = data.formattedDate;
    this.shifts = data.shifts;

    this.selection = new SelectionModel<GetEmployee>(true, []);

    let allEmployees: GetEmployee[] = [];
    for(let shift of this.shifts) {
      if (shift.activeEmployees) {
        allEmployees = shift.activeEmployees;
      }
    }

    this.selection = new SelectionModel<GetEmployee>(true, allEmployees);
  }

  ngOnInit(): void {
  }

  async search(){
    // Puffer damit der eingegebene Text lädt
    await new Promise(f => setTimeout(f, 100));
    this.selection.clear();

    for(let shift of this.shifts) {
      if (shift.activeEmployees) {
        this.selection = new SelectionModel<GetEmployee>(true, shift.activeEmployees);
      }

      shift.activeEmployees?.forEach(employee => this.selection.select(employee))

      this.searchKey = (document.getElementById("searchInput") as HTMLInputElement).value;
      if (this.searchKey != "") {
        let newFound = true;
        while (newFound) {
          newFound = false;
          let notFitting = this.selection.selected.find(value => {
            if (value.firstName && value.lastName) {
              if (value.firstName.includes(this.searchKey)) {
                return;
              }
              if (value.lastName.includes(this.searchKey)) {
                return;
              }
            }
            return value;
          })
          if (notFitting) {
           this.selection.deselect(notFitting);
            newFound = true;
          }
        }
      }
    }
  }

  checkForAbsence(shifts: GetShift[]): GetEmployee[] {
    let absentEmployees: GetEmployee[] = [];

    for(let shift of shifts) {

      if(shift.activeEmployees) {
        for(let employee of shift.activeEmployees) {

          if(employee.holidays) {
            for(let holiday of employee.holidays) {

              if(holiday.startDate && holiday.endDate) {
                if(this.date >= holiday.startDate && this.date <= holiday.endDate) {

                  absentEmployees.push(employee);
                }
              }
            }
          }
        }
      }
    }
  return absentEmployees;
  }
}
