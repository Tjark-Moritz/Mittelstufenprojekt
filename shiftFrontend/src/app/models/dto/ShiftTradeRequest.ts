import {Employee} from "./Employee";

export class ShiftTradeRequest {
  constructor(public shiftTradeId?: number,
              public requestingEmployee?: Employee,
              public replyingEmployee?: Employee,
              public oldShiftId?: number,
              public newShiftId?: number) {
  }
}
