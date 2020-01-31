import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableEventDayComponent } from './event-day-table.component';

describe('TableEventDayComponent', () => {
  let component: TableEventDayComponent;
  let fixture: ComponentFixture<TableEventDayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableEventDayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableEventDayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
