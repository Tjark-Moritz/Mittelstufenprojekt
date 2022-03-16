import {Employee} from "./Employee";

export class Shift {
  constructor(public attribute?: number,
              public activeEmployee?: Employee[],
              public shiftTypeId?: number,
              public shiftDate?: Date) {
  }
}
