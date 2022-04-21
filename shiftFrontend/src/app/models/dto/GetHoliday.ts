export class GetHoliday {
  constructor(public holidayId?: number,
              public holidayTypeId?: number,
              public startDate?: Date,
              public endDate?: Date,
              public employeeId?: number) {
  }
}
