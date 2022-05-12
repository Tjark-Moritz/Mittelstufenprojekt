import {GetShiftType} from "./GetShiftType";

export class AddEmployee {
  constructor(public username?: string,
              public lastName?: string,
              public firstName?: string,
              public street?: string,
              public zipcode?: string,
              public city?: string,
              public phone?: string,
              public email?: string,
              public numHolidaysLeft?: number,
              public base64ProfilePic?: string,
              public preferredShiftTypeId?: GetShiftType,
              public departmentId?: number,
  ) {
  }
}
