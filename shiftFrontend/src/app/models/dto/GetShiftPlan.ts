import {GetDepartment} from "./GetDepartment";
import {GetShift} from "./GetShift";
import {GetShiftType} from "./GetShiftType";

export class GetShiftPlan {
  constructor(public id?: number,
              public departmentId?: GetDepartment,
              public validMonth?: Date,
              public shifts?: GetShift[],
              public excludedWeekdays?: string[]) {
  }
}
