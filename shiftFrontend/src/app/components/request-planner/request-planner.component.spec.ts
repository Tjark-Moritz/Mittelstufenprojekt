import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestPlannerComponent } from './request-planner.component';

describe('RequestPlannerComponent', () => {
  let component: RequestPlannerComponent;
  let fixture: ComponentFixture<RequestPlannerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestPlannerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestPlannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
