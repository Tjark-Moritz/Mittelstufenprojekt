import {GetEmployee} from "./GetEmployee";
import {GetShift} from "./GetShift";

export class GetShiftTradeRequest {
  constructor(public id?: number,
              public requestingEmployee?: GetEmployee,
              public replyingEmployee?: GetEmployee,
              public oldShift?: GetShift,
              public newShift?: GetShift) {
  }
}
