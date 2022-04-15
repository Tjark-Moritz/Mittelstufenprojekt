import {Component, OnInit} from '@angular/core';

import {EmployeeService} from "../../services/employee.service";
import {ShiftPlan} from "../../models/dto/ShiftPlan";
import {AllEmployees} from "../../models/dto/AllEmployees";
import {Shift} from "../../models/dto/Shift";
import {ShiftType} from "../../models/dto/ShiftType";
import {Employee} from "../../Employee";

@Component({
  selector: 'app-shift-plan',
  templateUrl: './shift-plan.component.html',
  styleUrls: ['./shift-plan.component.css']
})

export class ShiftPlanComponent implements OnInit {
  display: string = "month";

  weekdays: string[] = ["Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"];

  monthStructure: Date[][] = [[],[],[],[],[],[]];
  weekStructure: Date[] = [];

  currentDate: Date = new Date();
  chosenDate: Date = new Date();


  shiftPlan = new ShiftPlan();


  constructor(private employeeService: EmployeeService) {
    let shift1Start = new Date();
    shift1Start.setHours(6, 0, 0);
    let shift1End = new Date();
    shift1End.setHours(14, 0, 0);
    let shift2End = new Date();
    shift2End.setHours(22, 0, 0);

    let shiftType1: ShiftType = new ShiftType(1, shift1Start, shift1End, "Frühschicht", "blau");
    let shiftType2: ShiftType = new ShiftType(2, shift1End, shift2End, "Spätschicht", "grün");

    let empList = new AllEmployees();
    this.employeeService.getAllEmployees().subscribe(val => empList = val)

    let shift1List: Employee[] = [];
    let shift2List: Employee[] = [];

    if(empList.employees) {
      shift1List.push(empList.employees[0]);
      shift1List.push(empList.employees[1]);
      shift2List.push(empList.employees[1]);
    }

    let shift1Date = new Date()
    shift1Date.setFullYear(2022, 1, 1)
    let shift2Date = new Date()
    shift2Date.setFullYear(2022, 1, 2)

    let shift1: Shift = new Shift(1, shift1List, 1, shift1Date);
    let shift2: Shift = new Shift(2, shift2List, 2, shift2Date);

    let shifts: Shift[] = [];
    shifts.push(shift1)
    shifts.push(shift2)

    let validMonthYear = new Date()
    validMonthYear.setFullYear(2022, 1)

    let shiftTypes: ShiftType[] = [];
    shiftTypes.push(shiftType1)
    shiftTypes.push(shiftType2)

    this.shiftPlan = new ShiftPlan(1, shifts, validMonthYear, shiftTypes)

    /*if(empList.employees[0] != undefined) {
      let currentUser: Employee = empList.employees[0];
    }*/
  }

  ngOnInit(): void {
    this.calculateMonthStructure();
    this.calculateWeekStructure();
    console.log(this.shiftPlan)
  }


  getTotalDaysInMonth(date: Date): number {
    return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
  }

  getFirstWeekdayOfMonth(date: Date): number {
    return new Date(date.getFullYear(), date.getMonth(), 1).getDay()-1;
  }

  getMonthTitle(month: number): string {
    let months: string[] = ["Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"];
    return months[month];
  }

  calculateMonthStructure(): void {
    let tempDate: Date = new Date();
    let weekCounter: number = 0;
    let dateCounter: number = 0;

    // Füllt monthStructure[0] teils mit Daten letzten Monats
    for(let weekday = 6; weekday >= 0; weekday--) {
      if(this.getFirstWeekdayOfMonth(this.chosenDate) <= weekday) {
        continue;
      }

      this.monthStructure[0][weekday] = new Date(this.chosenDate.getFullYear(), this.chosenDate.getMonth(), dateCounter);
      dateCounter--
    }

    // Füllt monthStructure[] mit Daten diesen Monats
    for(let day = 1; day <= this.getTotalDaysInMonth(this.chosenDate); day++) {
      if(this.monthStructure[weekCounter].length == 7) {
        weekCounter++;
      }

      tempDate = new Date(this.chosenDate.getFullYear(), this.chosenDate.getMonth(), day);
      this.monthStructure[weekCounter].push(tempDate)
    }

    // Ergänzt letzten Array der monthStructure[] Daten nächsten Monats
    for(let week = 0; week < this.monthStructure.length; week++) {
      dateCounter = this.getTotalDaysInMonth(this.chosenDate) + 1;
      while(this.monthStructure[week].length != 7 && this.monthStructure[week].length != 0) {
        this.monthStructure[week].push(new Date(this.chosenDate.getFullYear(), this.chosenDate.getMonth(), dateCounter));
        dateCounter++;
      }
    }
  }

  calculateWeekStructure(): void {
    weekLoop:
    for(let week = 0; week < 6; week++) {
      for(let day = 0; day < 7; day++) {
        if(this.monthStructure[week][day].toDateString() == this.chosenDate.toDateString()) {
          this.weekStructure = this.monthStructure[week];
          break weekLoop;
        }
      }
    }
  }


  previousMonth(): void {
    this.chosenDate.setDate(1);
    this.chosenDate.setMonth(this.chosenDate.getMonth()-1);
    this.updateMonthStructure();
  }

  nextMonth(): void {
    if(this.chosenDate.getFullYear() >= this.currentDate.getFullYear() && this.chosenDate.getMonth() >= this.currentDate.getMonth()) {
      return;
    }

    this.chosenDate.setDate(1);
    this.chosenDate.setMonth(this.chosenDate.getMonth()+1);
    this.updateMonthStructure();
  }

  updateMonthStructure(): void {
    this.monthStructure = [[],[],[],[],[],[]];
    this.calculateMonthStructure();
  }


  previousWeek(): void {
    this.chosenDate.setDate(this.chosenDate.getDate() - 7);
    this.updateWeekStructure();
}

  nextWeek(): void {
    let tempDate: Date = new Date(this.chosenDate.getFullYear(), this.chosenDate.getMonth(), this.chosenDate.getDate());
    tempDate.setDate(this.chosenDate.getDate() + 7)

    if(tempDate.getFullYear() >= this.currentDate.getFullYear() && tempDate.getMonth() > this.currentDate.getMonth()) {
      return;
    }

    this.chosenDate.setDate(this.chosenDate.getDate() + 7)
    this.updateWeekStructure();
  }

  updateWeekStructure(): void {
    this.weekStructure = [];
    this.updateMonthStructure();
    this.calculateWeekStructure();
  }


  openDatePicker(datepicker?: any) {
    datepicker.open();
  }

  closeDatePicker(eventData: any, datepicker?:any) {
    if(eventData.getMonth() == this.currentDate.getMonth() && eventData.getFullYear() == this.currentDate.getFullYear()) {
      eventData = this.currentDate
    }

    datepicker.close();
    this.chosenDate = eventData;
    this.updateMonthStructure()
  }


  getEmployeesFromShift(shiftPlan: ShiftPlan, date: Date): string {
    if(date.getMonth() != this.chosenDate.getMonth()) {
      return "";
    }

    if(shiftPlan.shifts == undefined || shiftPlan.shifts[date.getDate() - 1] == undefined || shiftPlan.shifts[date.getDate() - 1].activeEmployee == undefined) {
      return "";
    }

    let employees: string = ""

    // @ts-ignore
    for(let i = 0; i < shiftPlan.shifts[date.getDate() - 1].activeEmployee.length; i++) {
      // @ts-ignore
      if(shiftPlan.shifts[date.getDate() - 1].activeEmployee[i] != undefined) {
        // @ts-ignore
        employees += "\n" + shiftPlan.shifts[date.getDate() - 1].activeEmployee[i].firstname.toString() + shiftPlan.shifts[date.getDate() - 1].activeEmployee[i].lastname.toString() + ",";
      }
    }
    return employees
  }

  getShiftType(shiftPlan: ShiftPlan, date: Date): number {
    if(date.getMonth() != this.chosenDate.getMonth() || date == this.currentDate) {
      return 999;
    }

    if(shiftPlan.shifts != undefined && shiftPlan.shifts[date.getDate() - 1] != undefined) {
      // @ts-ignore
      return shiftPlan.shifts[date.getDate() - 1].shiftTypeId;
    }

    return 999
  }


  //TODO: colored shifts for current user
  //      which color is more important when overlapping? currentDate, lastMonth, shiftColor
  //      class.table-success, class.table-warning, class.table-info
  //      [class.table-success] = ""
  //      *ngFor="let day of monthStructure" : foreach shift of shiftPlan.shifts : if(shifts[shift].activeEmployee[].contains(currentUser) -> if(shiftType == 1) -> color 1 - elseif (shiftType == 2) -> color 2
  //      [class.table-success] = ""

  /*colorShift(shiftPlan: ShiftPlan, currentUser: Employee): string {
    if(shiftPlan.shifts == undefined) {
      return "";
    }

    for(let shift = 0; shift < shiftPlan.shifts?.length; shift++) {
      if(shiftPlan.shifts[shift].activeEmployee == undefined) {
        return "";
      }

      // @ts-ignore
      if(shiftPlan.shifts[shift].activeEmployee.indexOf(currentUser) != -1) {
        if(shiftPlan.shiftType?.shiftTypeColor == undefined) {
          return "";
        }
        return shiftPlan.shiftType?.shiftTypeColor.toString();
      }
    }
    return "";
  }*/
}
