export class AddDepartment {
  constructor(public name?: string,
              public abbreviatedName?: string,
              public leadEmployeeId?: number,
              public employeesIds?: number[]) {
  }
}
