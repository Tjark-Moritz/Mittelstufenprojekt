export class AddMessage {
  constructor(public id?: number,
              public channelId?: number,
              public requestedEmployeeId?: number,
              public sendingEmployeeId?: number,
              public LocalDateTime?: Date,
              public type?: number,
              public status?: string) {
  }
}
