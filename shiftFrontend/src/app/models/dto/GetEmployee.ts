import {GetHoliday} from "./GetHoliday";
import {GetSickDay} from "./GetSickDay";
import {GetShiftType} from "./GetShiftType";

export class GetEmployee {
  constructor(public id?: number,
              public username?: string,
              public lastname?: string,
              public firstname?: string,
              public street?: string,
              public zipcode?: string,
              public city?: string,
              public phone?: string,
              public email?: string,
              public numHolidaysLeft?: number,
              public acceptedHolidays?: GetHoliday[],
              public sickDays?: GetSickDay[],
              public base64ProfilePic?: string,
              public preferredShiftType?: GetShiftType,
              public departmentId?: number) {
  }
}