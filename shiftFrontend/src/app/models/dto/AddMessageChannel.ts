import {AddMessage} from "./AddMessage";

export class AddMessageChannel {
  constructor(public id?: number,
              public name?: string,
              public description?: string,
              public messages?: AddMessage[]) {
  }
}
