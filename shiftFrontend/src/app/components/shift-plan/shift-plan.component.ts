import {Component, OnInit} from '@angular/core';

import {EmployeeService} from "../../services/employee.service";
import {ShiftPlan} from "../../models/dto/ShiftPlan";
import {GetShift} from "../../models/dto/GetShift";
import {GetShiftType} from "../../models/dto/GetShiftType";
import {GetEmployee} from "../../models/dto/GetEmployee";
import {DayDetailsComponent} from "../day-details/day-details.component";
import {MatDialog} from "@angular/material/dialog";
import {ShiftPlanService} from "../../services/shift-plan.service";
import {GetShiftPlan} from "../../models/dto/GetShiftPlan";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-shift-plan',
  templateUrl: './shift-plan.component.html',
  styleUrls: ['./shift-plan.component.css']
})

export class ShiftPlanComponent implements OnInit {
  display: string = "month";

  weekdays: string[] = ["Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"];
  months: string[] = ["Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"]

  monthStructure: Date[][] = [[],[],[],[],[],[]];
  weekStructure: Date[] = [];

  currentDate: Date = new Date();
  chosenDate: Date = new Date();
  chosenShiftPlan: ShiftPlan = new ShiftPlan();

  currentUser: GetEmployee = new GetEmployee();

  empList: GetEmployee[] = [];
  _ShiftPlanList: GetShiftPlan[] = [];
  public get shiftPlanList(): GetShiftPlan[]{
    return this._ShiftPlanList;
  }
  public set shiftPlanList(val){
    this._ShiftPlanList = val;

    this.calculateMonthStructure();
    this.calculateWeekStructure();
  }

  constructor(private shiftplanService: ShiftPlanService, private employeeService: EmployeeService, private loginService: LoginService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    if(this.loginService.isUserLoggedIn()) {
      if(this.loginService.LoggedInUser.departmentId) {
        this.employeeService.getAllEmployees().subscribe(val => this.empList = val)
        this.shiftplanService.getShiftplansByDeptId(this.loginService.LoggedInUser.departmentId).subscribe(val => {
          this.shiftPlanList = val;
        })
      }
    }
  }


  getTotalDaysInMonth(date: Date): number {
    return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
  }

  getFirstWeekdayOfMonth(date: Date): number {
    return new Date(date.getFullYear(), date.getMonth(), 0).getDay();
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

  backToCurrentDate(datepicker?: any): void {
    this.chosenDate = new Date()
    this.updateMonthStructure()
    datepicker.close()
  }

  colorizeShifts(shiftPlan: ShiftPlan, date: Date): string {
    let shiftColor: string = "#FFFFFF"

    let shiftPlans: GetShiftPlan[] = this.shiftPlanList;
    if(shiftPlans.length > 0) {
      shiftPlans.forEach((value, index) => {
        if(!value.shifts)
          return;

        let shifts: GetShift[] = value.shifts!;
        let allShiftsAtdate: GetShift[] = shifts.filter(x => {
          let xDate: Date = new Date(x.shiftDate!);
          return xDate.toDateString() == date.toDateString()
        });
        let userShiftAtDate: GetShift | undefined;

        allShiftsAtdate.forEach((value, index) => {
          let empsInShift: GetEmployee[] | undefined = value.activeEmployees;
          let empFound: GetEmployee | undefined = empsInShift?.find( x=> x.id == this.loginService.LoggedInUser.id);
          if(empFound){
            userShiftAtDate = value;
          }
        });

        if(userShiftAtDate && userShiftAtDate.shiftType?.shiftTypeColor){
          shiftColor = userShiftAtDate.shiftType?.shiftTypeColor;
          console.log(shiftColor)
        }

      })
      if(shiftPlans[0].shifts) {

      }
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


  openDialog(shiftPlan: ShiftPlan, date: Date) {
    if(shiftPlan.shifts){
      let formattedDate: string = this.getFormattedDate(date);
      let shifts: GetShift[] = this.getShifts(shiftPlan, date)

      this.dialog.open(DayDetailsComponent, {
        position: {
          top: "0",
          right: "0",
        },
        height: "100vh",
        direction: "rtl",
        data: {
          date,
          formattedDate,
          shifts,
        }
      });
    }
  }

  getFormattedDate(date: Date): string {
    return this.weekdays[date.getDay()] + ", " + date.getDate() + "." + this.months[date.getMonth()] + " " + date.getFullYear();
  }

  getShifts(shiftPlan: ShiftPlan, date?: Date, shiftType?: GetShiftType): GetShift[] {
    if(shiftPlan.shifts) {
      if (date) {
        let chosenDayShifts: GetShift[];
        chosenDayShifts = shiftPlan.shifts.filter(shift => shift.shiftDate?.toDateString() == date.toDateString())

        if (shiftType) {
          return chosenDayShifts.filter(shift => shift.shiftType?.id == shiftType.id)
        }
        return chosenDayShifts
      }
      return shiftPlan.shifts
    }
    return []
  }

  getShiftTypes(shiftPlan: ShiftPlan): GetShiftType[] {
    let shiftTypes: GetShiftType[] = [];

    if(shiftPlan.shiftType) {
      for(let type of shiftPlan.shiftType) {
        shiftTypes.push(type)
      }
    }
    return shiftTypes
  }
}
