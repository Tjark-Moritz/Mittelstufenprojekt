import {GetShiftType} from "./GetShiftType";
import {GetShift} from "./GetShift";

export class ShiftPlan {
  constructor(public shiftPlanId?: number,
              public shifts?: GetShift[],
              public validMonthYear?: Date,
              public shiftType?: GetShiftType[]) {
  }
}
