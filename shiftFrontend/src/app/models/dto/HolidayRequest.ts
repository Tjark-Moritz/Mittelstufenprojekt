import {Holiday} from "./Holiday";

export class HolidayRequest {
  constructor(public holidayRequestId?: number,
              public requestingEmployeeId?: number,
              public holiday?: Holiday,
              public status?: boolean,
              public requestDate?: Date) {
  }
}
