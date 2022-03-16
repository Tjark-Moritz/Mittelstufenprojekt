import {Employee} from "./Employee";
import {ShiftPlan} from "./ShiftPlan";

export class Department {
  constructor(public departmentId?: number,
              public name?: string,
              public abbreviatedName?: string,
              public leadEmployee?: number,
              public employees?: Employee[]) {
  }
}
