import {HolidayType} from "./HolidayType";

export class Holiday {
  constructor(public holidayId?: number,
              public holidayType?: HolidayType,
              public startDate?: Date,
              public endDate?: Date) {
  }
}
