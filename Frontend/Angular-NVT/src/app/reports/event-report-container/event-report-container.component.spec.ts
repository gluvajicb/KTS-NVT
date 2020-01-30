import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventReportContainerComponent } from './event-report-container.component';

describe('EventReportContainerComponent', () => {
  let component: EventReportContainerComponent;
  let fixture: ComponentFixture<EventReportContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventReportContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventReportContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
