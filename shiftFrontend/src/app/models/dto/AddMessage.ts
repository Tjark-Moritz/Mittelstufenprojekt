export class AddMessage {
  constructor(public id?: number,
              public channelId?: number,
              public dateTime?: Date,
              public type?: number,
              public content?: string,
              public status?: string) {
  }
}
