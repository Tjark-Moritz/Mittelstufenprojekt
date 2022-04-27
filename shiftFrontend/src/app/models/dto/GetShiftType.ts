export class GetShiftType {
  constructor(public id?: number,
              public shiftStartTime?: Date,
              public endShiftTime?: Date,
              public typeName?: string,
              public shiftTypeColor?: string) {
  }
}
