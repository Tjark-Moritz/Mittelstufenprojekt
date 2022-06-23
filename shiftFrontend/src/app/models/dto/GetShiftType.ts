export class GetShiftType {
  constructor(public id?: number,
              public shiftStartTime?: Date,
              public shiftEndTime?: Date,
              public targetNumOfEmps?: number,
              public typeName?: string,
              public shiftTypeColor?: string) {
  }
}
