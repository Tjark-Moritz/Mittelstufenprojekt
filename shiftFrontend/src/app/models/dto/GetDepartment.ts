import {GetEmployee} from "./GetEmployee";

export class GetDepartment {
  constructor(public departmentId?: number,
              public name?: string,
              public abbreviatedName?: string,
              public leadEmployee?: number,
              public employees?: GetEmployee[]) {
  }
}
