import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HolidayPlanerComponent } from './holiday-planer.component';

describe('HolidayPlanerComponent', () => {
  let component: HolidayPlanerComponent;
  let fixture: ComponentFixture<HolidayPlanerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HolidayPlanerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HolidayPlanerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
