import {GetHoliday} from "./GetHoliday";
import {GetSickDay} from "./GetSickDay";
import {GetShiftType} from "./GetShiftType";

export class GetEmployee {
  constructor(public id?: number,
              public username?: string,
              public lastName?: string,
              public firstName?: string,
              public street?: string,
              public zipcode?: string,
              public city?: string,
              public phone?: string,
              public email?: string,
              public numHolidaysLeft?: number,
              public holidays?: GetHoliday[],
              public sickDays?: GetSickDay[],
              public base64ProfilePic?: string,
              public preferredShiftType?: GetShiftType,
              public departmentId?: number) {
  }
}
