import {AddShiftType} from "./AddShiftType";

export class AddShiftPlan {
  constructor(public departmentId?: number,
              public validMonth?: Date,
              public excludedWeekdays?: string[]) {
  }
}
