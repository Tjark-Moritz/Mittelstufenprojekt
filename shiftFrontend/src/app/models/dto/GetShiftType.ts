export class GetShiftType {
  constructor(public id?: number,
              public shiftStartTime?: Date,
              public shiftEndTime?: Date,
              public typeName?: string,
              public shiftTypeColor?: string) {
  }
}
