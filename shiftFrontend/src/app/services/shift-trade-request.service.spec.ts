import { TestBed } from '@angular/core/testing';

import { ShiftTradeRequestService } from './shift-trade-request.service';

describe('ShiftTradeRequestService', () => {
  let service: ShiftTradeRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShiftTradeRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
