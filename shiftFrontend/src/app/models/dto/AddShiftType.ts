export class AddShiftType {
  constructor(public shiftStartTime?: Date,
              public shiftEndTime?: Date,
              public targetNumOfEmps?: number,
              public typeName?: string,
              public shiftTypeColor?: string) {
  }
}
