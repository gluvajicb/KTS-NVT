import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTicketTableComponent } from './table.component';

describe('UserTicketTableComponent ', () => {
  let component: UserTicketTableComponent;
  let fixture: ComponentFixture<UserTicketTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserTicketTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserTicketTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
