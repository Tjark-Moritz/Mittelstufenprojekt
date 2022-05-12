import {AddShiftType} from "./AddShiftType";

export class AddShift {
  constructor(public shiftDate?: Date,
              public shiftType?: AddShiftType,
              public activeEmployeesIds?: number[]) {
  }
}
