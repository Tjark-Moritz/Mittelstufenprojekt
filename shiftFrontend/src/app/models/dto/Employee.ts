import {Holiday} from "./Holiday";
import {SickDay} from "./SickDay";
import {ShiftType} from "./ShiftType";

export class Employee {
  constructor(public employeeId?: number,
              public firstname?: string,
              public lastname?: string,
              public address?: string,
              public zipcode?: string,
              public city?: string,
              public telephone?: string,
              public email?: string,
              public takenHoliday?: Holiday[],
              public sickDays?: SickDay[],
              public numHolidaysLeft?: number,
              public encodededProfilePic?: string,
              public preferredShiftType?: ShiftType) {
  }
}
