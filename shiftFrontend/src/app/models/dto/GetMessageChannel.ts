import {GetMessage} from "./GetMessage";

export class GetMessageChannel {
  constructor(public id?: number,
              public name?: string,
              public description?: string,
              public messages?: GetMessage[],
              public employees?: number[]) {
  }
}
