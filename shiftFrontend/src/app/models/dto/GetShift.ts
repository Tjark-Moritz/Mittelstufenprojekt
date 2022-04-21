import {GetShiftType} from "./GetShiftType";
import {GetEmployee} from "./GetEmployee";

export class GetShift {
  constructor(public id?: number,
              public shiftDate?: Date,
              public shiftType?: GetShiftType,
              public activeEmployees?: GetEmployee[]) {
  }
}
