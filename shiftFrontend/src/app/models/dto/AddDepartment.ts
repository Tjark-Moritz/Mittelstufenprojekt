import {AddShiftType} from "./AddShiftType";

export class AddDepartment {
  constructor(public name?: string,
              public abbreviatedName?: string,
              public leadEmployeeId?: number,
              public employeeIds?: number[],
              public shiftTypes?: AddShiftType[]) {
  }
}
