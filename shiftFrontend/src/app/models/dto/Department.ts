import {GetEmployee} from "./GetEmployee";
// WIRD VERMUTLICH NOCH GEAENDERT
export class Department {
  constructor(public departmentId?: number,
              public name?: string,
              public abbreviatedName?: string,
              public leadEmployee?: number,
              public employees?: GetEmployee[]) {
  }
}
