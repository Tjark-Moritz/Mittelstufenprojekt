import {ShiftType} from "./ShiftType";

export class AddEmployee {
  constructor(public firstname?: string,
              public lastname?: string,
              public address?: string,
              public zipcode?: string,
              public city?: string,
              public telephone?: string,
              public email?: string,
              public numHolidaysLeft?: number,
              public preferredShiftType?: ShiftType,
              public encodededProfilePic?: string) {
  }
}
