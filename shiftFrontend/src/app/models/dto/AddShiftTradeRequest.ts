export class AddShiftTradeRequest {
  constructor(public requestingEmployeeId?: number,
              public replyingEmployeeId?: number,
              public oldShiftId?: number,
              public newShiftId?: number) {
  }
}
