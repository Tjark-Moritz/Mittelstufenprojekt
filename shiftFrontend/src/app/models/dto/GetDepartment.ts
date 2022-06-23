import {GetEmployee} from "./GetEmployee";
import {GetShiftType} from "./GetShiftType";

export class GetDepartment {
  constructor(public departmentId?: number,
              public name?: string,
              public abbreviatedName?: string,
              public leadEmployee?: number,
              public employees?: GetEmployee[],
              public shiftTypes?: GetShiftType) {
  }
}
