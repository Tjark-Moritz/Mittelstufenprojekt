export class AddMessage {
  constructor(public id?: number,
              public channelId?: number,
              public requestedEmployeeId?: number,
              public sendingEmployeeId?: number,
              public dateTime?: Date,
              public type?: number,
              public status?: string) {
  }
}
