export class AddHoliday {
  constructor(public holidayTypeId?: number,
              public startDate?: Date,
              public endDate?: Date,
              public employeeId?: number) {
  }
}
