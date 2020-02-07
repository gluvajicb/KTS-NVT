import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventReportContainerComponent } from './user-tickets-container.component';

describe('UserTicketsContainerComponent', () => {
  let component: UserTicketsContainerComponent;
  let fixture: ComponentFixture<UserTicketsContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserTicketsContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserTicketsContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
