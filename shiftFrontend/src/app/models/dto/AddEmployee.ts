import {GetShiftType} from "./GetShiftType";

export class AddEmployee {
  constructor(public username?: string,
              public lastname?: string,
              public firstname?: string,
              public street?: string,
              public zipcode?: string,
              public city?: string,
              public phone?: string,
              public email?: string,
              public numHolidaysLeft?: number,
              public base64ProfilePic?: string,
              public preferredShiftType?: GetShiftType,
              public departmentId?: number,
  ) {
  }
}
