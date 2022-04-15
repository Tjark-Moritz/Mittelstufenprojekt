import {ShiftType} from "./ShiftType";
import {Shift} from "./Shift";

export class ShiftPlan {
  constructor(public shiftPlanId?: number,
              public shifts?: Shift[],
              public validMonthYear?: Date,
              public shiftType?: ShiftType[]) {
  }
}
