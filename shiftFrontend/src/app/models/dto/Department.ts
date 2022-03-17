import {Employee} from "./Employee";

export class Department {
  constructor(public departmentId?: number,
              public name?: string,
              public abbreviatedName?: string,
              public leadEmployee?: number,
              public employees?: Employee[]) {
  }
}
