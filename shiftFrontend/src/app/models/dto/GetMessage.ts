export class GetMessage {
  constructor(public id?: number,
              public channelId?: number,
              public requestedEmployeeId?: number,
              public sendingEmployeeId?: number,
              public dateTime?: Date,
              public type?: number,
              public content?: string,
              public status?: string) {
  }
}
