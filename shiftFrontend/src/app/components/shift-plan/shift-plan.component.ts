import {Component, OnInit} from '@angular/core';

import {EmployeeService} from "../../services/employee.service";
import {ShiftPlan} from "../../models/dto/ShiftPlan";
import {GetShift} from "../../models/dto/GetShift";
import {GetShiftType} from "../../models/dto/GetShiftType";
import {GetEmployee} from "../../models/dto/GetEmployee";

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
  chosenShiftPlan: ShiftPlan = new ShiftPlan();

  currentUser: GetEmployee = new GetEmployee();

  constructor(private employeeService: EmployeeService) {
    let shift1Start = new Date();
    shift1Start.setHours(6, 0, 0);
    let shift1End = new Date();
    shift1End.setHours(14, 0, 0);
    let shift2End = new Date();
    shift2End.setHours(22, 0, 0);

    let shiftType1: GetShiftType = new GetShiftType(1, shift1Start, shift1End, "Frühschicht", "#B5FFE1");
    let shiftType2: GetShiftType = new GetShiftType(2, shift1End, shift2End, "Spätschicht", "#E5FFB5");

    let empList: GetEmployee[] = [];
    this.employeeService.getAllEmployees().subscribe(val => empList = val)

    let shift1List: GetEmployee[] = [];
    let shift2List: GetEmployee[] = [];

    if(empList) {
      shift1List.push(empList[0]);
      shift1List.push(empList[1]);
      shift2List.push(empList[1]);
    }

    let shift1Date = new Date()
    shift1Date.setFullYear(2022, 3, 1)
    let shift2Date = new Date()
    shift2Date.setFullYear(2022, 3, 2)

    let shift1: GetShift = new GetShift(1, shift1Date, shiftType1, shift1List);
    let shift2: GetShift = new GetShift(2, shift2Date, shiftType2, shift2List);

    let shifts: GetShift[] = [];
    shifts.push(shift1)
    shifts.push(shift2)

    let validMonthYear = new Date()
    validMonthYear.setFullYear(2022, 3)

    let shiftTypes: GetShiftType[] = [];
    shiftTypes.push(shiftType1)
    shiftTypes.push(shiftType2)

    this.chosenShiftPlan = new ShiftPlan(1, shifts, validMonthYear, shiftTypes)

    if(empList) {
      this.currentUser = empList[1];
    }
  }

  ngOnInit(): void {
    this.calculateMonthStructure();
    this.calculateWeekStructure();
    console.log(this.chosenShiftPlan)
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


  closeDatePicker(eventData: any, datepicker?:any) {
    if(eventData._d.getMonth() == this.currentDate.getMonth() && eventData._d.getFullYear() == this.currentDate.getFullYear()) {
      eventData._d = this.currentDate
    }

    this.chosenDate = eventData._d;
    this.updateMonthStructure()
    datepicker.close();
  }


  getEmployeesFromShift(shiftPlan: ShiftPlan, date: Date): GetEmployee[] {
    let employees: GetEmployee[] = [];

    if(date.getMonth() != this.chosenDate.getMonth()) {
      return employees;
    }

    if(!shiftPlan.shifts || !shiftPlan.shifts[date.getDate() - 1] || !shiftPlan.shifts[date.getDate() - 1].activeEmployees) {
      return employees;
    }

    // @ts-ignore
    for(let i = 0; i < shiftPlan.shifts[date.getDate() - 1].activeEmployee.length; i++) {
      // @ts-ignore
      if(shiftPlan.shifts[date.getDate() - 1].activeEmployee[i] != undefined) {
        // @ts-ignore
        employees.push(shiftPlan.shifts[date.getDate() - 1].activeEmployee[i])
      }
    }
    return employees
  }

  colorizeShifts(shiftPlan: ShiftPlan, date: Date): string {
    let shiftColor: string = "#FFFFFF"

    if(!shiftPlan.shifts || !shiftPlan.shiftType || !shiftPlan.validMonthYear) {
      return shiftColor;
    }

    if(this.currentDate.toDateString() == date.toDateString()) {
      shiftColor = "#C8F6FF"    // Lightblue
      return shiftColor
    }

    let tempShift = shiftPlan.shifts.find(shift => shift.shiftDate?.toDateString() == date.toDateString())
    let tempShiftType: GetShiftType | undefined;
    if(tempShift?.activeEmployees?.find(employee => employee.id == this.currentUser.id)) {
      tempShiftType = shiftPlan.shiftType.find(type => type.id == tempShift?.id);
      if(tempShiftType?.shiftTypeColor) {
        shiftColor = tempShiftType?.shiftTypeColor
      }
    }

    if(this.chosenDate.getMonth() != date.getMonth() && shiftColor == "#FFFFFF") {
      shiftColor = "#D3D3D3";   // Grey
    }

    return shiftColor;
  }

  setOpacity(shiftPlan: ShiftPlan, date: Date): string {
    let opacity: string = "1";
    if(this.chosenDate.getFullYear() != date.getFullYear() || this.chosenDate.getMonth() != date.getMonth()) {
      opacity = "0.5";
    }
    return opacity;
  }
}
